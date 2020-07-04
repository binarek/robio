package binarek.robio.user.domain.person;

import binarek.robio.codegen.ValueTypeStyle;
import com.fasterxml.jackson.annotation.JsonValue;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@ValueTypeStyle
abstract class PersonIdValue {

    @JsonValue
    @Value.Parameter
    public abstract UUID getValue();
}
