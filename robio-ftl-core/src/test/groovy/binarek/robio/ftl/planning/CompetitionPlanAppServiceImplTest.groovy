package binarek.robio.ftl.planning

import binarek.robio.ftl.planning.exception.CompetitionPlanAlreadyExistsException
import binarek.robio.ftl.planning.exception.CompetitionPlanNotFoundException
import binarek.robio.ftl.planning.model.CompetitionPlan
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
                assert runsLimitPerRobot == RUNS_LIMIT_PER_ROBOT
            }
        })
    }

    def 'throws exception if cannot initialize new plan'() {
        when: 'invoking plan initialization'
        competitionPlanAppService.initializePlan(initializeCompetitionPlanCommand())

        then: 'checks that plan cannot be initialized'
        1 * competitionPlanService.validateIfCanInitializeCompetitionPlan(COMPETITION_ID) >> {
            throw CompetitionPlanAlreadyExistsException.ofCompetitionId(COMPETITION_ID)
        }
        and: 'no plan is persisted'
        0 * competitionPlanRepository.save(_)

        then: 'validation exception is thrown'
        thrown(CompetitionPlanAlreadyExistsException)
    }

    def 'gets plan for existing competition id'() {
        given: 'existing competition plan'
        competitionPlanRepository.getByCompetitionId(COMPETITION_ID) >> Optional.of(competitionPlan())

        when: 'invoking getting plan'
        def result = competitionPlanAppService.getPlan(COMPETITION_ID)

        then:
        noExceptionThrown()
        and: 'returned plan is valid'
        result == competitionPlan()
    }

    def 'throws exception while getting plan for non-existing competition id'() {
        given: 'nonexistent competition plan id'
        competitionPlanRepository.getByCompetitionId(COMPETITION_ID) >> Optional.empty()

        when: 'invoking getting plan'
        competitionPlanAppService.getPlan(COMPETITION_ID)

        then: 'not found exception is thrown'
        thrown(CompetitionPlanNotFoundException)
    }
}
