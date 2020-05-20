package binarek.robio.common.domain.value;

import binarek.robio.codegen.ValueTypeStyle;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.util.Assert;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = Notes.class)
interface NotesType {

    @JsonValue
    @Value.Parameter
    String getValue();

    @Value.Check
    default void validate() {
        Assert.state(getValue().length() <= 1000, "Max allowed note length is 1000 characters");
    }
}
