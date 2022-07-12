package binarek.robio.ftl.adapter.persistence

import binarek.robio.ftl.CompetitionRepository
import binarek.robio.ftl.adapter.persistence.configuration.FtlBeanNames
import binarek.robio.ftl.adapter.persistence.db.tables.records.CompetitionRecord
import binarek.robio.ftl.model.Competition
import binarek.robio.ftl.model.CompetitionState
import binarek.robio.ftl.model.ImmutableCompetition
import binarek.robio.shared.model.CompetitionId
import com.fasterxml.jackson.databind.ObjectMapper
import org.jooq.DSLContext
import org.jooq.JSON
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import java.time.OffsetDateTime
import java.time.ZoneOffset

import static binarek.robio.ftl.adapter.persistence.db.tables.Competition.COMPETITION

@IntegrationTest
class CompetitionRepositoryIT extends Specification implements CompetitionFixture {

    @Subject
    @Autowired
    CompetitionRepository competitionRepository

    @Shared
    DSLContext dsl

    @Shared
    boolean dbInitialized = false

    @Autowired
    def setDSLContextAndInitializeDbData(@Qualifier(FtlBeanNames.DSL_CONTEXT) DSLContext dsl) {
        this.dsl = dsl
        if (!dbInitialized) {
            insertCompetition(initializedCompetition(), competitionRulesWithoutLimitJson())
            insertCompetition(startedCompetition(), competitionRulesJson())
            insertCompetition(finishedCompetition(), competitionRulesJson())
            this.dbInitialized = true
        }
    }

    def cleanupSpec() {
        deleteAllCompetitions()
    }

    def 'inserts new competition into database'() {
        given:
        def competitionIdToSave = unsavedCompetitionId()
        def competitionToSave = unsavedCompetition()
        def competitionRulesJsonToSave = competitionRulesWithoutLimitJson()
        and:
        def objectMapper = new ObjectMapper()

        when:
        competitionRepository.save(competitionToSave)

        then:
        def resultRecord = fetchCompetition(competitionIdToSave)
        and:
        resultRecord.isPresent()
        and:
        verifyAll(resultRecord.get()) {
            id != null
            competitionId == competitionIdToSave.value
            version == 1L
            objectMapper.readTree(rules.data()) == objectMapper.readTree(competitionRulesJsonToSave.data())
            state == competitionToSave.state.name()
            initializeDateTime == competitionToSave.initializeDateTime.value.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()
            startDateTime == competitionToSave.startDateTime?.value?.withZoneSameInstant(ZoneOffset.UTC)?.toLocalDateTime()
            finishDateTime == competitionToSave.finishDateTime?.value?.withZoneSameInstant(ZoneOffset.UTC)?.toLocalDateTime()
        }

        cleanup:
        deleteCompetition(competitionIdToSave)
    }

    def 'updates existing competition'() {
        given:
        def savedCompetitionId = unsavedCompetitionId()
        def initialCompetition = unsavedCompetition()
        and:
        insertCompetition(initialCompetition, competitionRulesWithoutLimitJson())
        and:
        def updatedCompetition = ImmutableCompetition.builder()
                .from(initialCompetition)
                .version(1L)
                .rules(competitionRules())
                .state(CompetitionState.STARTED)
                .startDateTime(startDateTime())
                .build()
        and:
        def objectMapper = new ObjectMapper()

        when:
        competitionRepository.save(updatedCompetition)

        then:
        def resultRecord = fetchCompetition(savedCompetitionId)
        and:
        resultRecord.isPresent()
        and:
        verifyAll(resultRecord.get()) {
            id != null
            competitionId == savedCompetitionId.value
            version == 2L
            objectMapper.readTree(rules.data()) == objectMapper.readTree(competitionRulesJson().data())
            state == updatedCompetition.state.name()
            initializeDateTime == updatedCompetition.initializeDateTime.value.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()
            startDateTime == updatedCompetition.startDateTime?.value?.withZoneSameInstant(ZoneOffset.UTC)?.toLocalDateTime()
            finishDateTime == updatedCompetition.finishDateTime?.value?.withZoneSameInstant(ZoneOffset.UTC)?.toLocalDateTime()
        }

        cleanup:
        deleteCompetition(savedCompetitionId)
    }

    def 'gets competition by competition id for #competition'() {
        when:
        def result = competitionRepository.getByCompetitionId(competition.competitionId)

        then:
        result.isPresent()
        and:
        verifyAll(result.get()) {
            competitionId == competition.competitionId
            version == competition.version
            rules == competition.rules
            state == competition.state
            initializeDateTime?.value == competition.initializeDateTime?.value?.withZoneSameInstant(TIMEZONE)
            startDateTime?.value == competition.startDateTime?.value?.withZoneSameInstant(TIMEZONE)
            finishDateTime?.value == competition.finishDateTime?.value?.withZoneSameInstant(TIMEZONE)
        }

        where:
        competition              | competitionRulesJson
        initializedCompetition() | competitionRulesWithoutLimitJson()
        startedCompetition()     | competitionRulesJson()
        finishedCompetition()    | competitionRulesJson()
    }

    def 'cannot get competition by competition id if competition does not exist'() {
        expect:
        competitionRepository.getByCompetitionId(unsavedCompetitionId()).isEmpty()
    }

    private def insertCompetition(Competition competition, JSON competitionRulesJson) {
        dsl.insertInto(COMPETITION)
                .columns(COMPETITION.COMPETITION_ID,
                        COMPETITION.VERSION,
                        COMPETITION.STATE,
                        COMPETITION.RULES,
                        COMPETITION.INITIALIZE_DATE_TIME,
                        COMPETITION.START_DATE_TIME,
                        COMPETITION.FINISH_DATE_TIME,
                        COMPETITION.CREATED_AT,
                        COMPETITION.CREATED_BY,
                        COMPETITION.MODIFIED_AT,
                        COMPETITION.MODIFIED_BY,
                        COMPETITION.MODIFY_PROCESS,
                )
                .values(competition.competitionId.value,
                        competition.version ?: 1L,
                        competition.state.name(),
                        competitionRulesJson,
                        competition.initializeDateTime.value.toLocalDateTime(),
                        competition.startDateTime?.value?.toLocalDateTime(),
                        competition.finishDateTime?.value?.toLocalDateTime(),
                        OffsetDateTime.parse('2021-08-01T07:15:32+01:00'),
                        'testuser',
                        OffsetDateTime.parse('2021-10-01T10:23:12+01:00'),
                        'testuser',
                        '/test-modify-process',
                )
                .execute()
    }

    private Optional<CompetitionRecord> fetchCompetition(CompetitionId competitionId) {
        return dsl.selectFrom(COMPETITION)
                .where(COMPETITION.COMPETITION_ID.eq(competitionId.value))
                .fetchOptional()
    }

    private def deleteCompetition(CompetitionId competitionId) {
        dsl.deleteFrom(COMPETITION)
                .where(COMPETITION.COMPETITION_ID.eq(competitionId.value))
                .execute()
    }

    private def deleteAllCompetitions() {
        dsl.deleteFrom(COMPETITION)
                .execute()
    }
}
