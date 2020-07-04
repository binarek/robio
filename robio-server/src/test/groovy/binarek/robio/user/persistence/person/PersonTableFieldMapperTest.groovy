package binarek.robio.user.persistence.person

import binarek.robio.user.domain.person.PersonSortableField
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static binarek.robio.db.tables.Person.PERSON

class PersonTableFieldMapperTest extends Specification {

    @Subject
    def personTableFieldMapper = new PersonTableFieldMapper()

    @Unroll
    def 'maps sortable field #sortableField to person record field'() {
        when:
        def tableField = personTableFieldMapper.toField(sortableField)
        then:
        tableField == PERSON.field(sortableField.name())
        where:
        sortableField << PersonSortableField.values().toList()
    }
}
