package binarek.robio.ftl.command;

import binarek.robio.shared.model.RobotId;
import binarek.robio.shared.model.RobotName;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

@Value.Immutable
@ValueDefStyle
interface RegisterRobotCommandDef {

    @Value.Parameter
    RobotId getRobotId();

    @Value.Parameter
    RobotName getName();
}
