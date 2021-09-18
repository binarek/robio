package binarek.robio.ftl.view;

import binarek.robio.ftl.model.CompetitionRules;
import binarek.robio.shared.model.CompetitionId;

public interface CompetitionPlanView {

    CompetitionId getCompetitionId();

    CompetitionRules getRules();
}
