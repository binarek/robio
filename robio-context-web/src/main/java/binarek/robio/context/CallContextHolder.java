package binarek.robio.context;

import binarek.robio.shared.CallContextProvider;
import binarek.robio.shared.model.CallContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CallContextHolder implements CallContextProvider {

    private static final ThreadLocal<CallContext> CALL_CONTEXT_HOLDER = new ThreadLocal<>();

    @Override
    public CallContext getContext() {
        return Objects.requireNonNull(CALL_CONTEXT_HOLDER.get(), "Call context is not set!");
    }

    public void setContext(CallContext callContext) {
        CALL_CONTEXT_HOLDER.set(callContext);
    }

    public void removeContext() {
        CALL_CONTEXT_HOLDER.remove();
    }
}
