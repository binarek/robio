package binarek.robio.core.domain.team;

import binarek.robio.codegen.ValueTypeStyle;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = ImmutableTeamId.class)
public abstract class TeamId {

    TeamId() {
    }

    public static TeamId of(UUID value) {
        return ImmutableTeamId.ofValue(value);
    }

    @JsonValue
    @Value.Parameter
    public abstract UUID getValue();
}
