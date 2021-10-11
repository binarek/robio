package binarek.robio.ftl.adapter.persistence

import binarek.robio.ftl.model.Competition
import binarek.robio.ftl.model.CompetitionRules
import binarek.robio.ftl.model.CompetitionState
import binarek.robio.ftl.model.ImmutableCompetition
import binarek.robio.shared.model.BusinessDateTime
import binarek.robio.shared.model.CompetitionId
import org.jooq.JSON

import java.time.ZoneOffset
import java.time.ZonedDateTime

trait CompetitionFixture extends SharedFixture {

    Competition unsavedCompetition() {
        return ImmutableCompetition.builder()
                .competitionId(unsavedCompetitionId())
                .state(CompetitionState.INITIALIZED)
                .rules(competitionRulesWithoutLimit())
                .initializeDateTime(initializeDateTime())
                .build()
    }

    Competition initializedCompetition() {
        return ImmutableCompetition.builder()
                .competitionId(initializedCompetitionId())
                .version(competitionVersion())
                .state(CompetitionState.INITIALIZED)
                .rules(competitionRulesWithoutLimit())
                .initializeDateTime(initializeDateTime())
                .build()
    }

    Competition startedCompetition() {
        return ImmutableCompetition.builder()
                .competitionId(startedCompetitionId())
                .version(competitionVersion())
                .state(CompetitionState.STARTED)
                .rules(competitionRules())
                .initializeDateTime(initializeDateTime())
                .startDateTime(startDateTime())
                .build()
    }

    Competition finishedCompetition() {
        return ImmutableCompetition.builder()
                .competitionId(finishedCompetitionId())
                .version(competitionVersion())
                .state(CompetitionState.FINISHED)
                .rules(competitionRules())
                .initializeDateTime(initializeDateTime())
                .startDateTime(startDateTime())
                .finishDateTime(finishDateTime())
                .build()
    }

    CompetitionId initializedCompetitionId() {
        return CompetitionId.of(UUID.fromString('9d704d21-1191-45ac-8ef8-53a298388fc2'))
    }

    CompetitionId startedCompetitionId() {
        return CompetitionId.of(UUID.fromString('2a2718ff-d2dd-4306-b8b2-44bc63bdf2cc'))
    }

    CompetitionId finishedCompetitionId() {
        return CompetitionId.of(UUID.fromString('8a44195d-16c9-4e7d-8335-2f8bfab7e7b7'))
    }

    CompetitionId unsavedCompetitionId() {
        return CompetitionId.of(UUID.fromString('03144e23-114e-4fc0-8f93-c769faaa1f62'))
    }

    Long competitionVersion() {
        return 8L
    }

    CompetitionRules competitionRules() {
        return CompetitionRules.builder()
                .minRobotsToStartCompetition(10)
                .runsLimitPerRobot(5)
                .build()
    }

    JSON competitionRulesJson() {
        return JSON.json('{"minRobotsToStartCompetition":10,"runsLimitPerRobot":5}')
    }

    CompetitionRules competitionRulesWithoutLimit() {
        return CompetitionRules.builder()
                .minRobotsToStartCompetition(10)
                .build()
    }

    JSON competitionRulesWithoutLimitJson() {
        return JSON.json('{"minRobotsToStartCompetition":10}')
    }

    BusinessDateTime initializeDateTime() {
        return BusinessDateTime.of(ZonedDateTime.of(2021, 10, 11, 12, 15, 33, 0, ZoneOffset.UTC))
    }

    BusinessDateTime startDateTime() {
        return BusinessDateTime.of(ZonedDateTime.of(2021, 10, 13, 12, 20, 15, 0, ZoneOffset.UTC))
    }

    BusinessDateTime finishDateTime() {
        return BusinessDateTime.of(ZonedDateTime.of(2021, 10, 23, 20, 54, 41, 0, ZoneOffset.UTC))
    }
}
