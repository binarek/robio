package binarek.robio.context;

import binarek.robio.shared.model.CorrelationId;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class CorrelationIdFactory {

    private final List<CustomCorrelationIdGenerator> customCorrelationIdGenerators;

    CorrelationIdFactory(List<CustomCorrelationIdGenerator> customCorrelationIdGenerators) {
        this.customCorrelationIdGenerators = customCorrelationIdGenerators;
    }

    public CorrelationId create(HttpServletRequest request) {
        final var headerCorrelationId = request.getHeader(ContextHeaders.CORRELATION_ID);
        if (StringUtils.isNotBlank(headerCorrelationId)) {
            return CorrelationId.of(headerCorrelationId);
        } else {
            return generate(request);
        }
    }

    private CorrelationId generate(HttpServletRequest request) {
        return customCorrelationIdGenerators.stream()
                .map(generator -> generator.generate(request))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseGet(CorrelationIdFactory::generateDefault);
    }

    private static CorrelationId generateDefault() {
        return CorrelationId.of(UUID.randomUUID().toString());
    }
}
