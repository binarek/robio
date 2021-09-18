package binarek.robio.ftl.command;

import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

@Value.Immutable
@ValueDefStyle
interface ChangeRobotQualificationCommandDef {

    @Value.Parameter
    CompetitionId getCompetitionId();

    @Value.Parameter
    RobotId getRobotId();
}
