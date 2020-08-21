package binarek.robio.registration.domain;


import binarek.robio.common.codegen.ValueTypeStyle;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.util.Assert;

import java.util.regex.Pattern;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = ImmutableLastName.class)
public abstract class LastName {

    private static final Pattern VALUE_PATTERN = Pattern.compile("^[A-Z][A-Za-z -']+$");  // todo

    LastName() {
    }

    public static LastName of(String value) {
        return ImmutableLastName.ofValue(value);
    }

    @JsonValue
    @Value.Parameter
    @Value.Redacted
    public abstract String getValue();

    @Value.Check
    protected void validate() {
        Assert.state(VALUE_PATTERN.matcher(getValue()).find(), "Invalid first name format");
    }
}
