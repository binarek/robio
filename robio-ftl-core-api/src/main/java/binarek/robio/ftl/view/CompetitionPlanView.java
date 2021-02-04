package binarek.robio.ftl.view;

import binarek.robio.ftl.model.CompetitionRules;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;

import java.util.List;

public interface CompetitionPlanView {

    CompetitionId getCompetitionId();

    List<RobotId> getRobots();

    CompetitionRules getRules();
}
