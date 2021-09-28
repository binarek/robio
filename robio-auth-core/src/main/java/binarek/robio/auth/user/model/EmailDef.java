package binarek.robio.auth.user.model;

import binarek.robio.util.codegen.AbstractSingleValue;
import binarek.robio.util.codegen.ValueDefStyle;
import org.apache.commons.validator.routines.EmailValidator;
import org.immutables.value.Value;
import org.springframework.util.Assert;

@Value.Immutable
@ValueDefStyle
abstract class EmailDef extends AbstractSingleValue<String> {

    @Override
    @Value.Parameter
    public abstract String getValue();

    @Value.Check
    protected void validate() {
        Assert.state(EmailValidator.getInstance().isValid(getValue()), "Invalid email format");
    }
}
