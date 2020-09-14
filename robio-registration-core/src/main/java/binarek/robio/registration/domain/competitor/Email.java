package binarek.robio.registration.domain.competitor;

import binarek.robio.common.codegen.ValueTypeStyle;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.validator.routines.EmailValidator;
import org.immutables.value.Value;
import org.springframework.util.Assert;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = ImmutableEmail.class)
public abstract class Email {

    Email() {
    }

    public static Email of(String value) {
        return ImmutableEmail.ofValue(value);
    }

    @JsonValue
    @Value.Parameter
    @Value.Redacted
    public abstract String getValue();

    @Value.Check
    protected void validate() {
        Assert.state(EmailValidator.getInstance().isValid(getValue()), "Invalid email format");
    }
}
