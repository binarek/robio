package binarek.robio.ftl.view;

import binarek.robio.ftl.model.RobotQualification;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;
import binarek.robio.shared.model.RobotName;

public interface RobotView {

    CompetitionId getCompetitionId();

    RobotId getRobotId();

    RobotName getName();

    RobotQualification getQualification();
}
