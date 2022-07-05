package binarek.robio.ftl.fixture

import binarek.robio.ftl.model.CompetitionRules
import binarek.robio.shared.model.BusinessDateTime
import binarek.robio.shared.model.CompetitionId
import binarek.robio.shared.model.RobotId
import binarek.robio.shared.model.RobotName

import java.time.ZoneId
import java.time.ZonedDateTime

class SampleData {

    static class CompetitionData {
        CompetitionId competitionId
        CompetitionRules rules
        BusinessDateTime initializeDate

        static CompetitionData ftl1 = new CompetitionData(
                competitionId: CompetitionId.of(UUID.fromString('9d704d21-1191-45ac-8ef8-53a298388fc2')),
                rules: CompetitionRules.builder().build(),
                initializeDate: BusinessDateTime.of(ZonedDateTime.of(2020, 12, 30, 12, 0, 1, 0, ZoneId.of('Europe/Warsaw'))),
        )
    }

    static class RobotData {
        RobotId robotId
        RobotName name

        static RobotData moric = new RobotData(
                robotId: RobotId.of(UUID.fromString('18b0dd72-3662-4fc8-9a4c-2b3297805ac0')),
                name: RobotName.of('Moric'),
        )

        static RobotData dixy = new RobotData(
                robotId: RobotId.of(UUID.fromString('33e072b8-a7b8-4ae4-b990-12452bbd37f8')),
                name: RobotName.of('Dixy'),
        )

        static RobotData finky = new RobotData(
                robotId: RobotId.of(UUID.fromString('26621156-cbc5-41a4-b403-67216fc87450')),
                name: RobotName.of('Finky'),
        )
    }
}
