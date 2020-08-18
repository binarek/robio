package binarek.robio.registration.persistence.team

import binarek.robio.registration.domain.common.value.CommonValueMapper
import binarek.robio.registration.domain.team.TeamSortableField
import binarek.robio.registration.domain.team.TeamValueMapper
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
