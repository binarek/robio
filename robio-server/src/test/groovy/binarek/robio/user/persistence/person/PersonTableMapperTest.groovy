package binarek.robio.user.persistence.person

import binarek.robio.common.domain.value.CommonValueMapper
import binarek.robio.user.domain.person.PersonSortableField
import binarek.robio.user.domain.person.PersonValueMapper
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static binarek.robio.db.tables.Person.PERSON

class PersonTableMapperTest extends Specification {

    @Subject
    def personTableMapper = new PersonTableMapperImpl(Stub(CommonValueMapper), Stub(PersonValueMapper))

    @Unroll
    def 'maps sortable field #sortableField to person table field'() {
        when:
        def tableField = personTableMapper.toField(sortableField)
        then:
        tableField == PERSON.field(sortableField.name())
        where:
        sortableField << PersonSortableField.values().toList()
    }
}
