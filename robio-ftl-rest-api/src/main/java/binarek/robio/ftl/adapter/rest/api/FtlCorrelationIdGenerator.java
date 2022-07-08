package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.context.CustomCorrelationIdGenerator;
import binarek.robio.shared.model.CorrelationId;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Component
class FtlCorrelationIdGenerator implements CustomCorrelationIdGenerator {

    @Override
    public CorrelationId generate(HttpServletRequest request) {
        if (request.getServletPath().startsWith("/ftl")) {
            return CorrelationId.of("ftl_" + UUID.randomUUID());
        }
        return null;
    }
}
