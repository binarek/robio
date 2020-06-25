package binarek.robio.user.domain.person;

import binarek.robio.codegen.ValueTypeStyle;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.util.Assert;

import java.util.regex.Pattern;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = FirstName.class)
interface FirstNameType {

    Pattern VALUE_PATTERN = Pattern.compile("^[^\\s\\d]+$");

    @JsonValue
    @Value.Parameter
    String getValue();

    @Value.Check
    default void validate() {
        Assert.state(VALUE_PATTERN.matcher(getValue()).find(), "Invalid character exists");
        Assert.state(Character.isUpperCase(getValue().charAt(0)), "Name has to start with uppercase character");
    }
}
