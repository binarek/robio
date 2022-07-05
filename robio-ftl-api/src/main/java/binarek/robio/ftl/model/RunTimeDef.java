package binarek.robio.ftl.model;

import binarek.robio.util.codegen.AbstractSingleValue;
import binarek.robio.util.codegen.ValueDefStyle;
import org.apache.commons.lang3.Validate;
import org.immutables.value.Value;

@Value.Immutable
@ValueDefStyle
abstract class RunTimeDef extends AbstractSingleValue<Long> implements Comparable<RunTimeDef> {

    @Value.Parameter
    @Override
    public abstract Long getValue();

    @Value.Check
    protected void validate() {
        Validate.isTrue(getValue() > 0, "Time needs to be positive");
    }

    @Override
    public int compareTo(RunTimeDef other) {
        return getValue().compareTo(other.getValue());
    }
}
