package binarek.robio.common.domain.entity

import binarek.robio.common.domain.value.Notes
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

    class Competition extends StubEntity {}

    class CompetitionRobot extends StubEntity {}

    class FTLRobot extends StubEntity {}

    class StubEntity implements Entity {

        @Override
        UUID getIdValue() {
            return Stub()
        }

        @Override
        Long getVersion() {
            return Stub()
        }

        @Override
        String getNameValue() {
            return Stub()
        }

        @Override
        Notes getNotes() {
            return Stub()
        }
    }
}
