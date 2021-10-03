package binarek.robio.auth.adapter.web;

import binarek.robio.auth.TokenService;
import binarek.robio.auth.exception.JwtValidationException;
import binarek.robio.auth.model.AccessToken;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class AccessTokenFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER_SCHEME_PREFIX = "Bearer ";
    private static final int AUTH_HEADER_SCHEME_PREFIX_LENGTH = 7;  // "Bearer ".length()

    private final TokenService tokenService;

    public AccessTokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            final var jwt = readJwt(request);
            if (jwt != null) {
                final var accessToken = tokenService.validateAndCreateAccessTokenFromJwt(jwt);
                final var authentication = buildAuthentication(accessToken, request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtValidationException e) {
            logger.debug(e.getLocalizedMessage(), e);
        }
        filterChain.doFilter(request, response);
    }

    @Nullable
    private String readJwt(HttpServletRequest request) {
        final var authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(AUTH_HEADER_SCHEME_PREFIX)) {
            return authHeader.substring(AUTH_HEADER_SCHEME_PREFIX_LENGTH);
        } else {
            return null;
        }
    }

    private UsernamePasswordAuthenticationToken buildAuthentication(AccessToken accessToken, HttpServletRequest request) {
        final var principal = accessToken.getClaims().getSubject().getValue();
        final var authorities = Set.of(new SimpleGrantedAuthority(accessToken.getClaims().getRole().name()));

        final var authentication = new UsernamePasswordAuthenticationToken(principal, null, authorities);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication;
    }
}
