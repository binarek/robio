package binarek.robio.user.domain.person;

import binarek.robio.codegen.ValueTypeStyle;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.validator.routines.EmailValidator;
import org.immutables.value.Value;
import org.springframework.util.Assert;

@Value.Immutable
@ValueTypeStyle
@JsonSerialize(as = ImmutableEmail.class)
public abstract class Email {

    Email() {
    }

    public static Email of(String value) {
        return ImmutableEmail.ofValue(value);
    }

    @JsonValue
    @Value.Parameter
    public abstract String getValue();

    @Value.Check
    protected void validate() {
        Assert.state(EmailValidator.getInstance().isValid(getValue()), "Invalid email format");
    }
}
