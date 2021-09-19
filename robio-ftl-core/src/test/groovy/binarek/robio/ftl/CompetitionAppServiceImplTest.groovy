package binarek.robio.ftl

import binarek.robio.ftl.exception.CompetitionAlreadyInitializedException
import binarek.robio.ftl.exception.CompetitionNotFoundException
import binarek.robio.ftl.model.Competition
import binarek.robio.shared.DateTimeProvider
import spock.lang.Specification
import spock.lang.Subject

class CompetitionAppServiceImplTest extends Specification implements CompetitionFixture {

    def competitionRepository = Mock(CompetitionRepository)
    def robotRepository = Mock(RobotRepository)
    def competitionService = Mock(CompetitionService)
    def dateTimeProvider = Stub(DateTimeProvider) {
        currentZonedDateTime() >> DATE_TIME
    }

    @Subject
    def competitionAppService = new CompetitionAppServiceImpl(competitionRepository, robotRepository, competitionService, dateTimeProvider)


    def 'initializes new competition from command'() {
        when: 'invokes competition initialization'
        competitionAppService.initializeCompetition(initializeCompetitionCommand())

        then: 'checks if competition can be initialized'
        1 * competitionService.validateIfCanInitializeCompetition(COMPETITION_ID)
        and: 'initialized competition is persisted'
        1 * competitionRepository.save({ Competition competition ->
            with(competition) {
                assert competitionId == COMPETITION_ID
                assert rules == competitionRules()
            }
        })
    }

    def 'throws exception if cannot initialize new competition'() {
        when: 'invoking competition initialization'
        competitionAppService.initializeCompetition(initializeCompetitionCommand())

        then: 'checks that competition cannot be initialized'
        1 * competitionService.validateIfCanInitializeCompetition(COMPETITION_ID) >> {
            throw CompetitionAlreadyInitializedException.of(COMPETITION_ID)
        }
        and: 'no competition is persisted'
        0 * competitionRepository.save(_)

        then: 'validation exception is thrown'
        thrown(CompetitionAlreadyInitializedException)
    }


    def 'gets competition for existing competition id'() {
        given: 'existing competition'
        competitionRepository.getByCompetitionId(COMPETITION_ID) >> Optional.of(competition())

        when: 'invoking getting competition'
        def result = competitionAppService.getCompetition(searchCompetitionCommand())

        then:
        noExceptionThrown()
        and: 'returned competition is valid'
        result == competition()
    }

    def 'throws exception while getting competition for non-existing competition id'() {
        given: 'nonexistent competition id'
        competitionRepository.getByCompetitionId(COMPETITION_ID) >> Optional.empty()

        when: 'invoking getting competition'
        competitionAppService.getCompetition(searchCompetitionCommand())

        then: 'not found exception is thrown'
        thrown(CompetitionNotFoundException)
    }


    def 'changes rules in existing competition'() {
        given: 'existing competition'
        competitionRepository.getByCompetitionId(COMPETITION_ID) >> Optional.of(competition())

        when: 'changing competition rules'
        competitionAppService.changeCompetitionRules(changeCompetitionRulesCommand())

        then:
        noExceptionThrown()
        and: 'new rules saved in repository'
        1 * competitionRepository.save({ Competition competition ->
            with(competition) {
                assert competitionId == COMPETITION_ID
                assert rules == changeCompetitionRulesCommand().rules
            }
        })
    }

    def 'throws exception while changing rules in non-existing competition'() {
        given: 'nonexistent competition'
        competitionRepository.getByCompetitionId(COMPETITION_ID) >> Optional.empty()

        when: 'changing competition rules'
        competitionAppService.changeCompetitionRules(changeCompetitionRulesCommand())

        then: 'not found exception is thrown'
        thrown(CompetitionNotFoundException)
        and: 'no competition is persisted'
        0 * competitionRepository.save(_)
    }


    def 'gets competition robots for existing competition id'() {
        given: 'existing competition'
        competitionRepository.existsByCompetitionId(COMPETITION_ID) >> true
        and: 'robots exist in competition'
        robotRepository.getByCompetitionId(COMPETITION_ID) >> robots()

        when: 'invoking getting competition robots'
        def result = competitionAppService.getCompetitionRobots(searchCompetitionCommand())

        then:
        noExceptionThrown()
        and: 'returned competition robots are valid'
        result == robots()
    }

    def 'throws exception while getting competition robots for non-existing competition id'() {
        given: 'nonexistent competition'
        competitionRepository.existsByCompetitionId(COMPETITION_ID) >> false

        when: 'invoking getting competition robots'
        competitionAppService.getCompetitionRobots(searchCompetitionCommand())

        then: 'not found exception is thrown'
        thrown(CompetitionNotFoundException)
        and: 'no robots get from repository'
        0 * robotRepository.getByCompetitionId(_)
    }
}
