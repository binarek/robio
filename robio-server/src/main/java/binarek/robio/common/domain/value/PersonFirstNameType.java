package binarek.robio.common.domain.value;

import binarek.robio.codegen.ValueTypeStyle;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.util.Assert;

import java.util.regex.Pattern;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = PersonFirstName.class)
interface PersonFirstNameType {

    Pattern VALUE_PATTERN = Pattern.compile("^[A-Z][a-z]+$"); // TODO

    @JsonValue
    @Value.Parameter
    String getValue();

    @Value.Check
    default void validate() {
        Assert.state(VALUE_PATTERN.matcher(getValue()).find(), "Invalid first name format");
    }
}
