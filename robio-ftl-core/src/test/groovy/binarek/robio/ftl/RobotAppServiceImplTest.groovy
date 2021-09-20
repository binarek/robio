package binarek.robio.ftl

import spock.lang.Specification
import spock.lang.Subject

class RobotAppServiceImplTest extends Specification implements RobotFixture {

    def robotRepository = Mock(RobotRepository)
    def robotService = Mock(RobotService)

    @Subject
    def robotAppService = new RobotAppServiceImpl(robotRepository, robotService)


    def 'gets robots for given competition id'() {
        given: 'robots exist in competition'
        robotRepository.getByCompetitionId(COMPETITION_ID) >> robots()

        when: 'invoking getting robots'
        def result = robotAppService.getRobots(robotsByCompetitionIdQuery())

        then:
        noExceptionThrown()
        and: 'returned robots are valid'
        result == robots()
    }
}
