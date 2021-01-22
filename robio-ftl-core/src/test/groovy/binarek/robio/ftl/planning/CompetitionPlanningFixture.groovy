package binarek.robio.ftl.planning

import binarek.robio.ftl.planning.command.InitializeCompetitionPlanCommand
import binarek.robio.ftl.planning.model.CompetitionPlan
import binarek.robio.ftl.planning.model.CompetitionRules
import binarek.robio.ftl.planning.model.RobotPlaceholder

trait CompetitionPlanningFixture {

    static UUID COMPETITION_ID = UUID.fromString('135be8e7-ef75-40a8-8893-6ab9f682b0df')
    static int RUNS_LIMIT_PER_ROBOT = 5

    InitializeCompetitionPlanCommand initializeCompetitionPlanCommand() {
        return InitializeCompetitionPlanCommand.builder()
                .competitionId(COMPETITION_ID)
                .rules(competitionRules())
                .build()
    }

    CompetitionPlan competitionPlan() {
        return CompetitionPlan.newPlan(COMPETITION_ID, competitionRules())
                .addRobots(readyRobotPlaceholder(), unreadyRobotPlaceholder())
    }

    RobotPlaceholder readyRobotPlaceholder() {
        return RobotPlaceholder.of(
                UUID.fromString('37cd59cf-592b-4f47-adb1-631795b25420'),
                UUID.fromString('cff8b0d0-494d-49ae-856d-e0452c57cca4'))
    }

    RobotPlaceholder unreadyRobotPlaceholder() {
        return RobotPlaceholder.of(
                null,
                UUID.fromString('ece90805-66b1-4df0-9872-6dfb1e1c6e84'))
    }

    CompetitionRules competitionRules() {
        return CompetitionRules.builder()
                .runsLimitPerRobot(RUNS_LIMIT_PER_ROBOT)
                .build()
    }
}
