package binarek.robio.core.persistence.robot

import binarek.robio.core.domain.robot.RobotSortableField
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static binarek.robio.db.tables.Robot.ROBOT

class RobotTableFieldMapperTest extends Specification {

    @Subject
    def robotTableFieldMapper = new RobotTableFieldMapper()

    @Unroll
    def 'maps sortable field #sortableField to robot record field'() {
        when:
        def tableField = robotTableFieldMapper.toField(sortableField)
        then:
        tableField == ROBOT.field(sortableField.name())
        where:
        sortableField << RobotSortableField.values().toList()
    }
}
