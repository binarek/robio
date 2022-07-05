package binarek.robio.ftl.fixture

import binarek.robio.ftl.model.ImmutableRobot
import binarek.robio.ftl.model.Robot
import binarek.robio.ftl.model.RobotQualification
import binarek.robio.shared.model.CompetitionId
import binarek.robio.shared.model.RobotId
import binarek.robio.shared.model.RobotName
import org.springframework.lang.Nullable

class RobotFixture {

    private CompetitionId competitionId
    private RobotId robotId
    @Nullable
    private Long version
    private RobotName name
    private RobotQualification qualification

    static RobotFixture robot(SampleData.RobotData robotData, SampleData.CompetitionData competitionData) {
        return new RobotFixture(
                competitionId: competitionData.competitionId,
                robotId: robotData.robotId,
                name: robotData.name,
                qualification: RobotQualification.PENDING,
        )
    }

    Robot build() {
        return ImmutableRobot.builder()
                .competitionId(competitionId)
                .robotId(robotId)
                .version(version)
                .name(name)
                .qualification(qualification)
                .build()
    }
}
