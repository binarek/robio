package binarek.robio.ftl.model;

import binarek.robio.util.codegen.AbstractSingleValue;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;
import org.springframework.util.Assert;

@Value.Immutable
@ValueDefStyle
abstract class RunTimeDef extends AbstractSingleValue<Long> {

    @Value.Parameter
    @Override
    public abstract Long getValue();

    @Value.Check
    protected void validate() {
        Assert.state(getValue() >= 0, "Time needs to be positive");
    }
}
