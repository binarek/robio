package binarek.robio.context;

import binarek.robio.shared.model.RequestContext;
import binarek.robio.shared.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("robioRequestContextFilter")
public class RequestContextFilter extends OncePerRequestFilter {

    private final RequestContextHolder requestContextHolder;

    public RequestContextFilter(RequestContextHolder requestContextHolder) {
        this.requestContextHolder = requestContextHolder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            requestContextHolder.setContext(createRequestContext());
            filterChain.doFilter(request, response);
        } finally {
            requestContextHolder.removeContext();
        }
    }

    private RequestContext createRequestContext() {
        return new RequestContext(getUser());
    }

    private User getUser() {
        final var principalName = SecurityContextHolder.getContext().getAuthentication().getName();
        return User.of(principalName);
    }
}
