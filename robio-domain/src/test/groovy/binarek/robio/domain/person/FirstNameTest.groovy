package binarek.robio.domain.person

import spock.lang.Specification
import spock.lang.Unroll

class FirstNameTest extends Specification {

    @Unroll
    def 'creates FirstName instance from valid first name "#value"'() {
        when:
        def firstName = FirstName.of(value)
        then:
        noExceptionThrown()
        firstName.getValue() == value
        where:
        value << ["John", "Łucja", "Павел"]
    }

    @Unroll
    def 'throws exception while creating FirstName instance from invalid first name like "#value"'() {
        when:
        FirstName.of(value)
        then:
        thrown(IllegalStateException)
        where:
        value << ["123", " ", "karol", "Marianna "]
    }

    def 'throws exception creating FirstName instance from null'() {
        when:
        FirstName.of(null)
        then:
        thrown(NullPointerException)
    }
}
