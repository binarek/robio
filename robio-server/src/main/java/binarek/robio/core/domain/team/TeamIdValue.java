package binarek.robio.core.domain.team;

import binarek.robio.codegen.ValueTypeStyle;
import com.fasterxml.jackson.annotation.JsonValue;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@ValueTypeStyle
abstract class TeamIdValue {

    @JsonValue
    @Value.Parameter
    public abstract UUID getValue();
}
