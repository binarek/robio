package binarek.robio.ftl

import binarek.robio.ftl.model.Robot
import binarek.robio.ftl.query.RobotsByCompetitionIdQuery
import binarek.robio.ftl.view.RobotView
import binarek.robio.shared.model.RobotId
import binarek.robio.shared.model.RobotName

trait RobotFixture extends CompetitionFixture {

    static RobotId ROBOT_1_ID = RobotId.of(UUID.fromString('ca988be2-ae00-41a5-b18d-5df29c0617f3'))
    static RobotName ROBOT_1_NAME = RobotName.of('Dingo')
    static RobotId ROBOT_2_ID = RobotId.of(UUID.fromString('81524c4b-9c8d-4269-b96c-9df2b9faeb00'))
    static RobotName ROBOT_2_NAME = RobotName.of('Speeder')

    RobotsByCompetitionIdQuery robotsByCompetitionIdQuery() {
        return RobotsByCompetitionIdQuery.builder().competitionId(COMPETITION_ID).build()
    }

    Collection<? extends RobotView> robots() {
        return [
                Robot.registerRobot(COMPETITION_ID, ROBOT_1_ID, ROBOT_1_NAME),
                Robot.registerRobot(COMPETITION_ID, ROBOT_2_ID, ROBOT_2_NAME)
        ]
    }
}