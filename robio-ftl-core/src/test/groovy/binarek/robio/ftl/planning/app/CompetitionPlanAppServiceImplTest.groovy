package binarek.robio.ftl.planning.app

import binarek.robio.ftl.planning.api.command.InitializeCompetitionPlanCommand
import binarek.robio.ftl.planning.api.exception.CompetitionPlanNotFoundException
import binarek.robio.ftl.planning.domain.CompetitionPlanRepository
import binarek.robio.ftl.planning.domain.model.CompetitionPlan
import spock.lang.Specification
import spock.lang.Subject

class CompetitionPlanAppServiceImplTest extends Specification implements CompetitionPlanningFixture {

    def competitionPlanRepository = Mock(CompetitionPlanRepository)

    @Subject
    def competitionPlanAppService = new CompetitionPlanAppServiceImpl(competitionPlanRepository)

    def 'initializes new plan from command'() {
        given:
        def command = InitializeCompetitionPlanCommand.builder()
                .competitionId(COMPETITION_ID)
                .runsLimitPerRobot(RUNS_LIMIT_PER_ROBOT)
                .build()
        when:
        competitionPlanAppService.initializePlan(command)
        then:
        1 * competitionPlanRepository.save({ CompetitionPlan plan ->
            with(plan) {
                assert competitionId == COMPETITION_ID
                assert runsLimitPerRobot == RUNS_LIMIT_PER_ROBOT
            }
        })
    }

    def 'gets plan for existing competition id'() {
        given:
        competitionPlanRepository.getByCompetitionId(COMPETITION_ID) >> Optional.of(competitionPlan())
        when:
        def result = competitionPlanAppService.getPlan(COMPETITION_ID)
        then:
        noExceptionThrown()
        and:
        result == competitionPlan()
    }

    def 'throws exception while getting plan for non-existing competition id'() {
        given:
        competitionPlanRepository.getByCompetitionId(COMPETITION_ID) >> Optional.empty()
        when:
        competitionPlanAppService.getPlan(COMPETITION_ID)
        then:
        thrown(CompetitionPlanNotFoundException)
    }
}
