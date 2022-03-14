package binarek.robio.context;

import binarek.robio.shared.RequestContextProvider;
import binarek.robio.shared.model.RequestContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RequestContextHolder implements RequestContextProvider {

    private static final ThreadLocal<RequestContext> REQUEST_CONTEXT_HOLDER = new ThreadLocal<>();

    @Override
    public RequestContext getContext() {
        return Objects.requireNonNull(REQUEST_CONTEXT_HOLDER.get(), "Request context is not set!");
    }

    public void setContext(RequestContext requestContext) {
        REQUEST_CONTEXT_HOLDER.set(requestContext);
    }

    public void removeContext() {
        REQUEST_CONTEXT_HOLDER.remove();
    }
}
