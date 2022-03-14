package binarek.robio.auth.adapter.security;

import binarek.robio.auth.model.Token;
import binarek.robio.auth.model.Username;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

public class TokenAuthentication extends AbstractAuthenticationToken {

    private final Username principal;

    private final Token credentials;

    public TokenAuthentication(Token token, HttpServletRequest request) {
        super(token.getAuthorities());
        this.principal = token.getUsername();
        this.credentials = token;
        setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    }

    @Override
    public Token getCredentials() {
        return credentials;
    }

    @Override
    public Username getPrincipal() {
        return principal;
    }
}
