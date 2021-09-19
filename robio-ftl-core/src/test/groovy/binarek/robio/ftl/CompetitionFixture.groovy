package binarek.robio.ftl

import binarek.robio.ftl.command.ChangeCompetitionRulesCommand
import binarek.robio.ftl.command.InitializeCompetitionCommand
import binarek.robio.ftl.command.SearchCompetitionCommand
import binarek.robio.ftl.model.Competition
import binarek.robio.ftl.model.CompetitionRules
import binarek.robio.ftl.model.Robot
import binarek.robio.ftl.view.RobotView
import binarek.robio.shared.model.CompetitionId
import binarek.robio.shared.model.RobotId
import binarek.robio.shared.model.RobotName

import java.time.ZoneOffset
import java.time.ZonedDateTime

trait CompetitionFixture {

    static CompetitionId COMPETITION_ID = CompetitionId.of(UUID.fromString('135be8e7-ef75-40a8-8893-6ab9f682b0df'))
    static int RUNS_LIMIT_PER_ROBOT = 5
    static int NEW_RUNS_LIMIT_PER_ROBOT = 13

    static RobotId ROBOT_1_ID = RobotId.of(UUID.fromString('ca988be2-ae00-41a5-b18d-5df29c0617f3'))
    static RobotName ROBOT_1_NAME = RobotName.of('Dingo')
    static RobotId ROBOT_2_ID = RobotId.of(UUID.fromString('81524c4b-9c8d-4269-b96c-9df2b9faeb00'))
    static RobotName ROBOT_2_NAME = RobotName.of('Speeder')

    static ZonedDateTime DATE_TIME = ZonedDateTime.of(2021, 10, 13, 12, 20, 15, 902, ZoneOffset.UTC)

    InitializeCompetitionCommand initializeCompetitionCommand() {
        return InitializeCompetitionCommand.of(COMPETITION_ID, competitionRules())
    }

    ChangeCompetitionRulesCommand changeCompetitionRulesCommand() {
        return ChangeCompetitionRulesCommand.of(COMPETITION_ID, competitionRules(NEW_RUNS_LIMIT_PER_ROBOT))
    }

    SearchCompetitionCommand searchCompetitionCommand() {
        return SearchCompetitionCommand.of(COMPETITION_ID)
    }

    Competition competition() {
        return Competition.initialize(COMPETITION_ID, competitionRules(), DATE_TIME)
    }

    CompetitionRules competitionRules(runsLimitPerRobot = RUNS_LIMIT_PER_ROBOT) {
        return CompetitionRules.builder()
                .runsLimitPerRobot(RUNS_LIMIT_PER_ROBOT)
                .build()
    }

    Collection<? extends RobotView> robots() {
        return [
                Robot.registerRobot(COMPETITION_ID, ROBOT_1_ID, ROBOT_1_NAME),
                Robot.registerRobot(COMPETITION_ID, ROBOT_2_ID, ROBOT_2_NAME)
        ]
    }
}
