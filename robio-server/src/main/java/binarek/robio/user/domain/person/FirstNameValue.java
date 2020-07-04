package binarek.robio.user.domain.person;

import binarek.robio.codegen.ValueTypeStyle;
import com.fasterxml.jackson.annotation.JsonValue;
import org.immutables.value.Value;
import org.springframework.util.Assert;

import java.util.regex.Pattern;

@Value.Immutable
@ValueTypeStyle
abstract class FirstNameValue {

    private static final Pattern VALUE_PATTERN = Pattern.compile("^[^\\s\\d]+$");

    @JsonValue
    @Value.Parameter
    @Value.Redacted
    public abstract String getValue();

    @Value.Check
    protected void validate() {
        Assert.state(VALUE_PATTERN.matcher(getValue()).find(), "Invalid character exists");
        Assert.state(Character.isUpperCase(getValue().charAt(0)), "Name has to start with uppercase character");
    }
}
