package binarek.robio.core.persistence.robot

import binarek.robio.common.domain.value.CommonValueMapper
import binarek.robio.core.domain.robot.RobotSortableField
import binarek.robio.core.domain.robot.RobotValueMapper
import binarek.robio.core.domain.team.TeamValueMapper
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static binarek.robio.db.tables.Robot.ROBOT

class RobotTableMapperTest extends Specification {

    @Subject
    def robotTableMapper = new RobotTableMapperImpl(
            Stub(CommonValueMapper),
            Stub(RobotValueMapper),
            Stub(TeamValueMapper))

    @Unroll
    def 'maps sortable field #sortableField to robot table field'() {
        when:
        def tableField = robotTableMapper.toField(sortableField)
        then:
        tableField == ROBOT.field(sortableField.name())
        where:
        sortableField << RobotSortableField.values().toList()
    }
}
