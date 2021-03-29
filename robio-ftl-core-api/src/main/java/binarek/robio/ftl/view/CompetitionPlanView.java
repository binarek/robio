package binarek.robio.ftl.view;

import binarek.robio.ftl.model.CompetitionRules;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;

import java.util.Set;

public interface CompetitionPlanView {

    CompetitionId getCompetitionId();

    Set<RobotId> getRobots();

    CompetitionRules getRules();
}
