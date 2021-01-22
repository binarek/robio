package binarek.robio.ftl.planning.view;

import binarek.robio.ftl.planning.model.CompetitionRules;

import java.util.List;
import java.util.UUID;

public interface CompetitionPlanView {

    UUID getCompetitionId();

    List<? extends RobotPlaceholderView> getRobots();

    CompetitionRules getRules();
}
