package binarek.robio.common.domain.value;

import binarek.robio.codegen.ValueTypeStyle;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.util.Assert;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = ImmutableNotes.class)
public abstract class Notes {

    Notes() {
    }

    public static Notes of(String value) {
        return ImmutableNotes.ofValue(value);
    }

    @JsonValue
    @Value.Parameter
    public abstract String getValue();

    @Value.Check
    protected void validate() {
        Assert.state(getValue().length() <= 1000, "Max allowed note length is 1000 characters");
    }
}
