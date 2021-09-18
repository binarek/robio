package binarek.robio.ftl

import binarek.robio.ftl.command.ChangeCompetitionPlanRulesCommand
import binarek.robio.ftl.command.InitializeCompetitionPlanCommand
import binarek.robio.ftl.command.SearchCompetitionPlanCommand
import binarek.robio.ftl.model.CompetitionPlan
import binarek.robio.ftl.model.CompetitionRules
import binarek.robio.shared.model.CompetitionId

trait CompetitionPlanningFixture {

    static CompetitionId COMPETITION_ID = CompetitionId.of(UUID.fromString('135be8e7-ef75-40a8-8893-6ab9f682b0df'))
    static int RUNS_LIMIT_PER_ROBOT = 5
    static int NEW_RUNS_LIMIT_PER_ROBOT = 13

    InitializeCompetitionPlanCommand initializeCompetitionPlanCommand() {
        return InitializeCompetitionPlanCommand.of(COMPETITION_ID, competitionRules())
    }

    ChangeCompetitionPlanRulesCommand changePlanRulesCommand() {
        return ChangeCompetitionPlanRulesCommand.of(COMPETITION_ID, competitionRules(NEW_RUNS_LIMIT_PER_ROBOT))
    }

    SearchCompetitionPlanCommand searchCompetitionPlanCommand() {
        return SearchCompetitionPlanCommand.of(COMPETITION_ID)
    }

    CompetitionPlan competitionPlan() {
        return CompetitionPlan.initializePlan(COMPETITION_ID, competitionRules())
    }

    CompetitionRules competitionRules(runsLimitPerRobot = RUNS_LIMIT_PER_ROBOT) {
        return CompetitionRules.builder()
                .runsLimitPerRobot(RUNS_LIMIT_PER_ROBOT)
                .build()
    }
}
