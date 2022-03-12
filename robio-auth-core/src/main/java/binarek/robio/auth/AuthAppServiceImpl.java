package binarek.robio.auth;

import binarek.robio.auth.model.*;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static binarek.robio.auth.model.Authorities.GENERATE_TOKENS;

@Service
class AuthAppServiceImpl implements AuthAppService {

    private static final Logger logger = LoggerFactory.getLogger(AuthAppServiceImpl.class);

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
    @PreAuthorize("hasAuthority('" + GENERATE_TOKENS + "')")
    public TokensPair generateTokens() {
        final var userIdAndRole = retrieveUserIdAndRole();

        final var tokens = tokenFactory.generateNewTokensForUser(userIdAndRole.getLeft(), userIdAndRole.getRight());
        refreshTokenWhitelistRepository.add(tokens.getRefreshToken());
        return tokens;
    }

    @Override
    public Token parseJwtToValidToken(String jwt) {
        return tokenFactory.createValidTokenFromJwt(jwt);
    }

    @Override
    public void validateTokenCredentials(Token token) {
        if (token instanceof RefreshToken refreshToken) {
            validateRefreshTokenCredentials(refreshToken);
        } else if (!(token instanceof AccessToken)) {
            throw new BadCredentialsException("Unknown type of token");
        }
    }

    private Pair<UserId, UserRole> retrieveUserIdAndRole() {
        final var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetailsImpl userDetails) {
            final var user = userDetails.getUser();
            return Pair.of(user.getUserId(), user.getRole());

        } else if (principal instanceof UserId userId) {
            final var role = userRepository.getRoleByUserId(userId)
                    .orElseThrow(() -> new IllegalStateException("Cannot find user with id " + userId));
            return Pair.of(userId, role);

        } else {
            throw new IllegalArgumentException("Cannot handle principal of type " + principal.getClass());
        }
    }

    private void validateRefreshTokenCredentials(RefreshToken refreshToken) {
        final boolean tokenDeletedFromWhitelist = refreshTokenWhitelistRepository.removeByTokenId(refreshToken.getClaims().getTokenId());
        if (!tokenDeletedFromWhitelist) {
            final var userId = refreshToken.getUserId();
            logger.warn("Suspicious request for user id {} - refresh token is not in whitelist. Removing all user tokens.", userId);
            refreshTokenWhitelistRepository.removeAllByUserId(userId);
            throw new BadCredentialsException("Refresh token is missing in whitelist");
        }
    }
}
