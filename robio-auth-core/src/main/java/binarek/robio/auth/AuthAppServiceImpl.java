package binarek.robio.auth;

import binarek.robio.auth.model.AccessToken;
import binarek.robio.auth.model.TokensPair;
import binarek.robio.auth.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
class AuthAppServiceImpl implements AuthAppService {

    private final TokenFactory tokenFactory;

    AuthAppServiceImpl(TokenFactory tokenFactory) {
        this.tokenFactory = tokenFactory;
    }

    @Override
    public TokensPair generateTokens(Authentication authentication) {
        checkIfAuthenticated(authentication);
        final var user = getUser(authentication);
        return tokenFactory.generateNewTokensForUser(user);
    }

    @Override
    public AccessToken validateAndParseAccessJwt(String jwt) {
        return tokenFactory.createValidAccessTokenFromJwt(jwt);
    }

    private static void checkIfAuthenticated(Authentication authentication) {
        Assert.isTrue(authentication.isAuthenticated(), "User is not authenticated");
    }

    private static User getUser(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetailsImpl) {
            return ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        } else {
            throw new IllegalStateException("Cannot resolve user for principal class " + authentication.getPrincipal().getClass());
        }
    }
}
