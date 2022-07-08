package binarek.robio.shared.model;

import binarek.robio.util.codegen.AbstractSingleValue;
import binarek.robio.util.codegen.ValueDefStyle;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.immutables.value.Value;

import static binarek.robio.util.StringUtil.isTrimmed;

@Value.Immutable
@ValueDefStyle
abstract class CorrelationIdDef extends AbstractSingleValue<String> {

    @Value.Parameter
    @Override
    public abstract String getValue();

    @Value.Check
    protected CorrelationIdDef normalizeAndValidate() {
        final var correlationId = getValue();
        if (!isTrimmed(correlationId)) {
            return CorrelationId.of(correlationId.trim());
        } else {
            Validate.isTrue(StringUtils.isNotBlank(getValue()), "Correlation id cannot be blank");
            return this;
        }
    }
}
