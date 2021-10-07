package binarek.robio.auth;

import binarek.robio.auth.model.AccessToken;
import binarek.robio.auth.model.RefreshToken;
import binarek.robio.auth.model.TokensPair;
import binarek.robio.auth.view.TokenView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
class AuthAppServiceImpl implements AuthAppService {

    private static final Logger logger = LoggerFactory.getLogger(AuthAppService.class);

    private final TokenFactory tokenFactory;
    private final UserRepository userRepository;
    private final RefreshTokenWhitelistRepository refreshTokenWhitelistRepository;

    AuthAppServiceImpl(TokenFactory tokenFactory,
                       UserRepository userRepository,
                       RefreshTokenWhitelistRepository refreshTokenWhitelistRepository) {
        this.tokenFactory = tokenFactory;
        this.userRepository = userRepository;
        this.refreshTokenWhitelistRepository = refreshTokenWhitelistRepository;
    }

    @Override
    public TokensPair generateTokens(Object principal) {
        if (principal instanceof UserDetailsImpl) {
            return generateTokens((UserDetailsImpl) principal);
        } else if (principal instanceof RefreshToken) {
            return generateTokens((RefreshToken) principal);
        } else if (principal instanceof AccessToken) {
            throw new AccessDeniedException("Cannot generate new tokens using access token");
        } else {
            throw new IllegalStateException("Cannot generate tokens for principal class " + principal.getClass());
        }
    }

    @Override
    public TokenView validateAndParseJwt(String jwt) {
        return tokenFactory.createValidTokenFromJwt(jwt);
    }

    private TokensPair generateTokens(UserDetailsImpl userDetails) {
        final var tokens = tokenFactory.generateNewTokensForUser(userDetails.getUser());
        refreshTokenWhitelistRepository.add(tokens.getRefreshToken());
        return tokens;
    }

    private TokensPair generateTokens(RefreshToken refreshToken) {
        final var userId = refreshToken.getClaims().getUserId();
        final boolean tokenDeletedFromWhitelist = refreshTokenWhitelistRepository.removeByTokenId(refreshToken.getClaims().getTokenId());

        if (!tokenDeletedFromWhitelist) {
            logger.warn("Suspicious request of token generation for user id {} - refresh token is not in whitelist. Removing all user tokens.", userId);
            refreshTokenWhitelistRepository.removeAllByUserId(userId);
            throw new AccessDeniedException("Cannot generate tokens");
        }

        final var user = userRepository.getByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("Cannot find user with id " + userId));
        final var tokens = tokenFactory.generateNewTokensForUser(user);
        refreshTokenWhitelistRepository.add(tokens.getRefreshToken());
        return tokens;
    }
}
