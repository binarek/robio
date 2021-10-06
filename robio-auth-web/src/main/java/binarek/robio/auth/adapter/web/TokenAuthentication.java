package binarek.robio.auth.adapter.web;

import binarek.robio.auth.view.AccessTokenView;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

public class TokenAuthentication extends AbstractAuthenticationToken {

    private final Object principal;

    public TokenAuthentication(AccessTokenView accessToken, HttpServletRequest request) {
        super(accessToken.getAuthorities());
        this.principal = accessToken.getSubject();
        setAuthenticated(true);
        setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    }

    @Override
    @Nullable
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
