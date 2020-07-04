package binarek.robio.common.domain.value;

import binarek.robio.codegen.ValueTypeStyle;
import com.fasterxml.jackson.annotation.JsonValue;
import org.immutables.value.Value;
import org.springframework.util.Assert;

@Value.Immutable
@ValueTypeStyle
abstract class NotesValue {

    @JsonValue
    @Value.Parameter
    public abstract String getValue();

    @Value.Check
    protected void validate() {
        Assert.state(getValue().length() <= 1000, "Max allowed note length is 1000 characters");
    }
}
