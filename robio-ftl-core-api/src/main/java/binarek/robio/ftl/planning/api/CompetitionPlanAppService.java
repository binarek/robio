package binarek.robio.ftl.planning.api;

import binarek.robio.ftl.planning.api.command.InitializeCompetitionPlanCommand;
import binarek.robio.ftl.planning.api.query.CompetitionPlanView;

import java.util.UUID;

public interface CompetitionPlanAppService {

    void initializePlan(InitializeCompetitionPlanCommand command);

    CompetitionPlanView getPlan(UUID competitionId);
}
