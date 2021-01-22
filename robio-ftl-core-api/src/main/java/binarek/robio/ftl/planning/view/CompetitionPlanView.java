package binarek.robio.ftl.planning.view;

import binarek.robio.ftl.model.CompetitionRules;
import binarek.robio.shared.model.CompetitionId;

import java.util.List;

public interface CompetitionPlanView {

    CompetitionId getCompetitionId();

    List<? extends RobotPlaceholderView> getRobots();

    CompetitionRules getRules();
}
