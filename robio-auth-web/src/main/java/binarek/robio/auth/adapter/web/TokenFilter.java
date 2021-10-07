package binarek.robio.auth.adapter.web;

import binarek.robio.auth.AuthAppService;
import binarek.robio.auth.exception.JwtValidationException;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class TokenFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER_SCHEME_PREFIX = "Bearer ";
    private static final int AUTH_HEADER_SCHEME_PREFIX_LENGTH = 7;  // "Bearer ".length()

    private final AuthAppService authAppService;

    public TokenFilter(AuthAppService authAppService) {
        this.authAppService = authAppService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            final var jwt = readJwt(request);
            if (jwt != null) {
                final var token = authAppService.validateAndParseJwt(jwt);
                final var authentication = new TokenAuthentication(token, request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtValidationException e) {
            logger.debug(e.getLocalizedMessage(), e);
        }
        filterChain.doFilter(request, response);
    }

    @Nullable
    private static String readJwt(HttpServletRequest request) {
        final var authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(AUTH_HEADER_SCHEME_PREFIX)) {
            return authHeader.substring(AUTH_HEADER_SCHEME_PREFIX_LENGTH);
        } else {
            return null;
        }
    }
}
