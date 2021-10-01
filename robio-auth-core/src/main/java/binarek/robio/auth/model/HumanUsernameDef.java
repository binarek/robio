package binarek.robio.auth.model;

import binarek.robio.util.codegen.AbstractSingleValue;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;
import org.springframework.util.Assert;

import static binarek.robio.auth.model.Username.isHumanUsername;

@Value.Immutable
@ValueDefStyle
abstract class HumanUsernameDef extends AbstractSingleValue<String> implements Username {

    @Override
    @Value.Parameter
    public abstract String getValue();

    @Value.Check
    protected void validate() {
        Assert.state(isHumanUsername(getValue()), () -> getValue() + " is not valid human username");
    }
}
