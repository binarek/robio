package binarek.robio.user.domain.person;

import binarek.robio.codegen.ValueTypeStyle;
import com.fasterxml.jackson.annotation.JsonValue;
import org.immutables.value.Value;
import org.springframework.util.Assert;

import java.util.regex.Pattern;

@Value.Immutable
@ValueTypeStyle
abstract class LastNameValue {

    private static final Pattern VALUE_PATTERN = Pattern.compile("^[A-Z][A-Za-z -']+$");  // todo

    @JsonValue
    @Value.Parameter
    @Value.Redacted
    public abstract String getValue();

    @Value.Check
    protected void validate() {
        Assert.state(VALUE_PATTERN.matcher(getValue()).find(), "Invalid first name format");
    }
}
