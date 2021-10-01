package binarek.robio.auth.model;

import binarek.robio.util.codegen.AbstractSingleValue;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;
import org.springframework.util.Assert;

@Value.Immutable
@ValueDefStyle
abstract class FirstNameDef extends AbstractSingleValue<String> {

    @Value.Parameter
    @Override
    public abstract String getValue();

    @Value.Check
    protected void validate() {
        final var name = getValue();
        Assert.state(name.length() == name.trim().length(), "First name needs to be trimmed");
        Assert.state(name.length() >= 2, "First name must have at least 2 characters length");
        Assert.state(name.matches("^\\S*$"), "First name cannot contain any whitespace");
        Assert.state(name.matches("^[^0-9!@#$%^&*()\\-_=+\\[\\]{};:'\",<.>/?]*$"), "First name contains invalid character");
    }
}
