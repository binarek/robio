package binarek.robio.registration.domain.common.entity

import spock.lang.Specification
import spock.lang.Unroll

class EntityUtilTest extends Specification {

    @Unroll
    def 'gets entity class name "#entityName" if class is #entityClass'() {
        expect:
        EntityUtil.name(entityClass) == entityName
        where:
        entityClass      || entityName
        Entity           || 'Entity'
        Competition      || 'Competition'
        CompetitionRobot || 'CompetitionRobot'
        FTLRobot         || 'FTLRobot'
    }

    class Competition implements Entity {}

    class CompetitionRobot implements Entity {}

    class FTLRobot implements Entity {}
}
