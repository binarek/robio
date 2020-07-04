package binarek.robio.user.domain.person;

import binarek.robio.codegen.ValueTypeStyle;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.validator.routines.EmailValidator;
import org.immutables.value.Value;
import org.springframework.util.Assert;

@Value.Immutable
@ValueTypeStyle
abstract class EmailValue {

    @JsonValue
    @Value.Parameter
    public abstract String getValue();

    @Value.Check
    protected void validate() {
        Assert.state(EmailValidator.getInstance().isValid(getValue()), "Invalid email format");
    }
}
