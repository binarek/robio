package binarek.robio.auth.adapter.web;

import binarek.robio.auth.view.TokenView;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

public class TokenAuthentication extends AbstractAuthenticationToken {

    private final Object principal;

    public TokenAuthentication(TokenView token, HttpServletRequest request) {
        super(token.getAuthorities());
        this.principal = token;
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
