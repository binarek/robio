package binarek.robio.ftl

import binarek.robio.ftl.command.ChangeCompetitionPlanRulesCommand
import binarek.robio.ftl.command.InitializeCompetitionPlanCommand
import binarek.robio.ftl.command.SearchCompetitionPlanCommand
import binarek.robio.ftl.model.CompetitionPlan
import binarek.robio.ftl.model.CompetitionRules
import binarek.robio.shared.model.CompetitionId
import binarek.robio.shared.model.RobotId

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
        return CompetitionPlan.newPlan(COMPETITION_ID, competitionRules())
                .addRobots(
                        RobotId.of(UUID.fromString('37cd59cf-592b-4f47-adb1-631795b25420')),
                        RobotId.of(UUID.fromString('ece90805-66b1-4df0-9872-6dfb1e1c6e84')))
    }

    CompetitionRules competitionRules(runsLimitPerRobot = RUNS_LIMIT_PER_ROBOT) {
        return CompetitionRules.builder()
                .runsLimitPerRobot(RUNS_LIMIT_PER_ROBOT)
                .build()
    }
}
