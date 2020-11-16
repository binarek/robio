package binarek.robio.ftl.planning.app

import binarek.robio.ftl.planning.domain.CompetitionPlanRepository
import binarek.robio.ftl.planning.domain.model.CompetitionPlan
import binarek.robio.ftl.planning.api.command.InitializeCompetitionPlanCommand
import binarek.robio.ftl.planning.api.exception.CompetitionPlanNotFoundException
import spock.lang.Specification
import spock.lang.Subject

class CompetitionPlanAppServiceImplTest extends Specification implements CompetitionPlanningFixture {

    def competitionPlanRepository = Mock(CompetitionPlanRepository)
    def modelMapper = Mock(FtlPlanningModelMapper)

    @Subject
    def ftlCompetitionPlanAppService = new CompetitionPlanAppServiceImpl(competitionPlanRepository, modelMapper)

    def 'initializes new plan from command'() {
        given:
        def command = InitializeCompetitionPlanCommand.builder()
                .competitionId(COMPETITION_ID)
                .runsLimitPerRobot(RUNS_LIMIT_PER_ROBOT)
                .build()
        when:
        ftlCompetitionPlanAppService.initializePlan(command)
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
        and:
        modelMapper.toCompetitionPlanView(competitionPlan()) >> ftlCompetitionPlan()
        when:
        def result = ftlCompetitionPlanAppService.getPlan(COMPETITION_ID)
        then:
        noExceptionThrown()
        and:
        result == ftlCompetitionPlan()
    }

    def 'throws exception while getting plan for non-existing competition id'() {
        given:
        competitionPlanRepository.getByCompetitionId(COMPETITION_ID) >> Optional.empty()
        when:
        ftlCompetitionPlanAppService.getPlan(COMPETITION_ID)
        then:
        thrown(CompetitionPlanNotFoundException)
    }
}
