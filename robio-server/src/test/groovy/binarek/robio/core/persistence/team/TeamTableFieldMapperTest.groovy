package binarek.robio.core.persistence.team

import binarek.robio.core.domain.team.TeamSortableField
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static binarek.robio.db.tables.Team.TEAM

class TeamTableFieldMapperTest extends Specification {

    @Subject
    def teamTableFieldMapper = new TeamTableFieldMapper()

    @Unroll
    def 'maps sortable field #sortableField to team record field'() {
        when:
        def tableField = teamTableFieldMapper.toField(sortableField)
        then:
        tableField == TEAM.field(sortableField.name())
        where:
        sortableField << TeamSortableField.values().toList()
    }
}
