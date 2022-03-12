package binarek.robio.auth.adapter.security;

import binarek.robio.auth.AuthAppService;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class TokenAuthenticationProvider implements AuthenticationProvider {

    private final AuthAppService authAppService;

    TokenAuthenticationProvider(AuthAppService authAppService) {
        this.authAppService = authAppService;
    }

    @Override
    @Nullable
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }
        final var tokenAuthentication = (TokenAuthentication) authentication;
        final var token = tokenAuthentication.getCredentials();

        authAppService.validateTokenCredentials(token);

        tokenAuthentication.setAuthenticated(true);
        return tokenAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthentication.class.isAssignableFrom(authentication);
    }
}
