package binarek.robio.registration.domain.common.value

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class CommonValueMapperTest extends Specification {

    @Subject
    def commonValueMapper = new CommonValueMapper()

    @Shared
    def stringNote = 'Some note text, text, text...'

    def 'maps Notes class to string'() {
        expect:
        commonValueMapper.toValue(Notes.of(stringNote)) == stringNote
    }

    def 'maps string to Notes class'() {
        expect:
        commonValueMapper.toNotes(stringNote) == Notes.of(stringNote)
    }
}
