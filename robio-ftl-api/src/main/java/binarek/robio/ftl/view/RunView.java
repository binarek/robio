package binarek.robio.ftl.view;

import binarek.robio.ftl.model.RunJudgement;
import binarek.robio.ftl.model.RunTime;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;

public interface RunView {

    CompetitionId getCompetitionId();

    RobotId getRobotId();

    Integer getNumber();

    RunJudgement getJudgement();

    RunTime getTime();
}
