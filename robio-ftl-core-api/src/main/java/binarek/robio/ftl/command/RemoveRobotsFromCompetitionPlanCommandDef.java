package binarek.robio.ftl.command;

import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

import java.util.Set;

@Value.Immutable
@ValueDefStyle
interface RemoveRobotsFromCompetitionPlanCommandDef {

    @Value.Parameter
    CompetitionId getCompetitionId();

    @Value.Parameter
    Set<RobotId> getRobots();
}
