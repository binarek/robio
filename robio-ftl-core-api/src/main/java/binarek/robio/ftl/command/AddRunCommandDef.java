package binarek.robio.ftl.command;

import binarek.robio.ftl.model.RunJudgement;
import binarek.robio.ftl.model.RunTime;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

@Value.Immutable
@ValueDefStyle
interface AddRunCommandDef {

    CompetitionId getCompetitionId();

    RobotId getRobotId();

    RunJudgement getJudgement();

    RunTime getTime();
}
