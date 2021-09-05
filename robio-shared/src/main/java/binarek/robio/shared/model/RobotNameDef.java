package binarek.robio.shared.model;

import binarek.robio.util.codegen.AbstractSingleValue;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;
import org.springframework.util.Assert;

@Value.Immutable
@ValueDefStyle
abstract class RobotNameDef extends AbstractSingleValue<String> {

    @Value.Parameter
    @Override
    public abstract String getValue();

    @Value.Check
    protected void validate() {
        final var name = getValue();
        Assert.state(name.length() == name.trim().length(), "Name needs to be trimmed");
        Assert.state(name.length() >= 3, "Name needs to have at least 3 characters length");
    }
}
