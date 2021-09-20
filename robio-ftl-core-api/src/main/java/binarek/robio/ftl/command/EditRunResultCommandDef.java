package binarek.robio.ftl.command;

import binarek.robio.ftl.model.RunJudgement;
import binarek.robio.ftl.model.RunTime;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
@ValueDefStyle
interface EditRunResultCommandDef {

    CompetitionId getCompetitionId();

    RobotId getRobotId();

    Integer getNumber();

    @Nullable
    RunJudgement getJudgement();

    @Nullable
    RunTime getTime();
}
