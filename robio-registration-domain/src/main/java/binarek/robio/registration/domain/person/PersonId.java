package binarek.robio.registration.domain.person;

import binarek.robio.common.codegen.ValueTypeStyle;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = ImmutablePersonId.class)
public abstract class PersonId {

    PersonId() {
    }

    public static PersonId of(UUID value) {
        return ImmutablePersonId.ofValue(value);
    }

    @JsonValue
    @Value.Parameter
    public abstract UUID getValue();
}
