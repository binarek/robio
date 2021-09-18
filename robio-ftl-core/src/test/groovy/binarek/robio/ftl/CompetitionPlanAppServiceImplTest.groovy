package binarek.robio.ftl

import binarek.robio.ftl.exception.CompetitionPlanAlreadyInitializedException
import binarek.robio.ftl.exception.CompetitionPlanNotFoundException
import binarek.robio.ftl.model.CompetitionPlan
import spock.lang.Specification
import spock.lang.Subject

class CompetitionPlanAppServiceImplTest extends Specification implements CompetitionPlanningFixture {

    def competitionPlanRepository = Mock(CompetitionPlanRepository)
    def competitionPlanService = Mock(CompetitionPlanService)

    @Subject
    def competitionPlanAppService = new CompetitionPlanAppServiceImpl(competitionPlanRepository, competitionPlanService)

    def 'initializes new plan from command'() {
        when: 'invokes plan initialization'
        competitionPlanAppService.initializePlan(initializeCompetitionPlanCommand())

        then: 'checks if plan can be initialized'
        1 * competitionPlanService.validateIfCanInitializeCompetitionPlan(COMPETITION_ID)
        and: 'initialized plan is persisted'
        1 * competitionPlanRepository.save({ CompetitionPlan plan ->
            with(plan) {
                assert competitionId == COMPETITION_ID
                assert rules == competitionRules()
            }
        })
    }

    def 'throws exception if cannot initialize new plan'() {
        when: 'invoking plan initialization'
        competitionPlanAppService.initializePlan(initializeCompetitionPlanCommand())

        then: 'checks that plan cannot be initialized'
        1 * competitionPlanService.validateIfCanInitializeCompetitionPlan(COMPETITION_ID) >> {
            throw CompetitionPlanAlreadyInitializedException.of(COMPETITION_ID)
        }
        and: 'no plan is persisted'
        0 * competitionPlanRepository.save(_)

        then: 'validation exception is thrown'
        thrown(CompetitionPlanAlreadyInitializedException)
    }

    def 'gets plan for existing competition id'() {
        given: 'existing competition plan'
        competitionPlanRepository.getByCompetitionId(COMPETITION_ID) >> Optional.of(competitionPlan())

        when: 'invoking getting plan'
        def result = competitionPlanAppService.getPlan(searchCompetitionPlanCommand())

        then:
        noExceptionThrown()
        and: 'returned plan is valid'
        result == competitionPlan()
    }

    def 'throws exception while getting plan for non-existing competition id'() {
        given: 'nonexistent competition plan id'
        competitionPlanRepository.getByCompetitionId(COMPETITION_ID) >> Optional.empty()

        when: 'invoking getting plan'
        competitionPlanAppService.getPlan(searchCompetitionPlanCommand())

        then: 'not found exception is thrown'
        thrown(CompetitionPlanNotFoundException)
    }

    def 'changes rules in existing plan'() {
        given: 'existing competition plan'
        competitionPlanRepository.getByCompetitionId(COMPETITION_ID) >> Optional.of(competitionPlan())

        when: 'changing plan rules'
        competitionPlanAppService.changePlanRules(changePlanRulesCommand())

        then:
        noExceptionThrown()
        and: 'new rules saved in repository'
        1 * competitionPlanRepository.save({ CompetitionPlan plan ->
            with(plan) {
                assert competitionId == COMPETITION_ID
                assert rules == changePlanRulesCommand().rules
            }
        })
    }

    def 'throws exception while changing rules in non-existing plan'() {
        given: 'nonexistent plan'
        competitionPlanRepository.getByCompetitionId(COMPETITION_ID) >> Optional.empty()

        when: 'changing plan rules'
        competitionPlanAppService.changePlanRules(changePlanRulesCommand())

        then: 'not found exception is thrown'
        thrown(CompetitionPlanNotFoundException)
        and: 'no plan is persisted'
        0 * competitionPlanRepository.save(_)
    }
}
