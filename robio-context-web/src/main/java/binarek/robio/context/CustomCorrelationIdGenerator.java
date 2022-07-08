package binarek.robio.context;

import binarek.robio.shared.model.CorrelationId;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface CustomCorrelationIdGenerator {

    /**
     * Generates correlation id to be used in call context or null.
     * Null value is expected if another correlation id algorithm generation should be used.
     *
     * @param request request
     * @return correlation id or null
     */
    @Nullable
    CorrelationId generate(HttpServletRequest request);
}
