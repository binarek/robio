package binarek.robio.ftl

import binarek.robio.ftl.command.ChangeCompetitionRulesCommand
import binarek.robio.ftl.command.InitializeCompetitionCommand
import binarek.robio.ftl.model.Competition
import binarek.robio.ftl.model.CompetitionRules
import binarek.robio.ftl.query.CompetitionByIdQuery
import binarek.robio.shared.model.CompetitionId

import java.time.ZoneOffset
import java.time.ZonedDateTime

trait CompetitionFixture {

    static CompetitionId COMPETITION_ID = CompetitionId.of(UUID.fromString('135be8e7-ef75-40a8-8893-6ab9f682b0df'))
    static int RUNS_LIMIT_PER_ROBOT = 5
    static int NEW_RUNS_LIMIT_PER_ROBOT = 13

    static ZonedDateTime DATE_TIME = ZonedDateTime.of(2021, 10, 13, 12, 20, 15, 902, ZoneOffset.UTC)

    InitializeCompetitionCommand initializeCompetitionCommand() {
        return InitializeCompetitionCommand.of(COMPETITION_ID, competitionRules())
    }

    ChangeCompetitionRulesCommand changeCompetitionRulesCommand() {
        return ChangeCompetitionRulesCommand.of(COMPETITION_ID, competitionRules(NEW_RUNS_LIMIT_PER_ROBOT))
    }

    CompetitionByIdQuery competitionByIdQuery() {
        return CompetitionByIdQuery.of(COMPETITION_ID)
    }

    Competition competition() {
        return Competition.initialize(COMPETITION_ID, competitionRules(), DATE_TIME)
    }

    CompetitionRules competitionRules(runsLimitPerRobot = RUNS_LIMIT_PER_ROBOT) {
        return CompetitionRules.builder()
                .runsLimitPerRobot(RUNS_LIMIT_PER_ROBOT)
                .build()
    }
}
