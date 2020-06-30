package binarek.robio.user.domain.person;

import binarek.robio.codegen.ValueTypeStyle;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.validator.routines.EmailValidator;
import org.immutables.value.Value;
import org.springframework.util.Assert;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = Email.class)
abstract class EmailValue {

    @JsonValue
    @Value.Parameter
    public abstract String getValue();

    @Value.Check
    protected void validate() {
        Assert.state(EmailValidator.getInstance().isValid(getValue()), "Invalid email format");
    }
}
