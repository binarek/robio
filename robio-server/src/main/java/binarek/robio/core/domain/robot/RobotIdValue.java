package binarek.robio.core.domain.robot;

import binarek.robio.codegen.ValueTypeStyle;
import com.fasterxml.jackson.annotation.JsonValue;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@ValueTypeStyle
abstract class RobotIdValue {

    @JsonValue
    @Value.Parameter
    public abstract UUID getValue();
}
