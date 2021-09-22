package binarek.robio.core.persistence.team

import binarek.robio.common.domain.value.CommonValueMapper
import binarek.robio.core.domain.team.TeamSortableField
import binarek.robio.core.domain.team.TeamValueMapper
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static binarek.robio.db.tables.Team.TEAM

class TeamTableMapperTest extends Specification {

    @Subject
    def teamTableMapper = new TeamTableMapperImpl(
            Stub(CommonValueMapper),
            Stub(TeamValueMapper),
            Stub(TeamMemberTableMapper))

    @Unroll
    def 'maps sortable field #sortableField to team table field'() {
        when:
        def tableField = teamTableMapper.toField(sortableField)
        then:
        tableField == TEAM.field(sortableField.name())
        where:
        sortableField << TeamSortableField.values().toList()
    }
}
