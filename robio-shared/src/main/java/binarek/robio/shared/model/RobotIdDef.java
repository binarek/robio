package binarek.robio.shared.model;

import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@ValueDefStyle
interface RobotIdDef {

    @Value.Parameter
    UUID getValue();
}
