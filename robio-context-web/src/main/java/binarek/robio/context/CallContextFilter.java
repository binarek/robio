package binarek.robio.context;

import binarek.robio.shared.model.CallContext;
import binarek.robio.shared.model.CorrelationId;
import binarek.robio.shared.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("robioCallContextFilter")
public class CallContextFilter extends OncePerRequestFilter {

    private final CallContextHolder callContextHolder;
    private final CorrelationIdFactory correlationIdFactory;

    CallContextFilter(CallContextHolder callContextHolder, CorrelationIdFactory correlationIdFactory) {
        this.callContextHolder = callContextHolder;
        this.correlationIdFactory = correlationIdFactory;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            final var callContext = createCallContext(request);
            setCallContext(callContext);
            setCallContextInResponse(response, callContext);
            filterChain.doFilter(request, response);
        } finally {
            unsetCallContext();
        }
    }

    private CallContext createCallContext(HttpServletRequest request) {
        return CallContext.of(getUser(), getProcessName(request), getCorrelationId(request));
    }

    private void setCallContext(CallContext callContext) {
        callContextHolder.setContext(callContext);
    }

    private void unsetCallContext() {
        callContextHolder.removeContext();
    }

    private void setCallContextInResponse(HttpServletResponse response, CallContext callContext) {
        response.setHeader(ContextHeaders.CORRELATION_ID, callContext.getCorrelationId().getValue());
    }

    private String getProcessName(HttpServletRequest request) {
        return request.getServletPath();
    }

    private CorrelationId getCorrelationId(HttpServletRequest request) {
        return correlationIdFactory.create(request);
    }

    private User getUser() {
        final var principalName = SecurityContextHolder.getContext().getAuthentication().getName();
        return User.of(principalName);
    }
}
