package binarek.robio.core.domain.robot;

import binarek.robio.codegen.ValueTypeStyle;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = ImmutableRobotId.class)
public abstract class RobotId {

    RobotId() {
    }

    public static RobotId of(UUID value) {
        return ImmutableRobotId.ofValue(value);
    }

    @JsonValue
    @Value.Parameter
    public abstract UUID getValue();
}
