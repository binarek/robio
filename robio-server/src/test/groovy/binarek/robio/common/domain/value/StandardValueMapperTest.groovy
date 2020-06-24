package binarek.robio.common.domain.value

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class StandardValueMapperTest extends Specification {

    @Subject
    def standardValueMapper = new StandardValueMapper()

    @Shared
    def stringNote = 'Some note text, text, text...'

    def 'maps Notes class to string'() {
        expect:
        standardValueMapper.toValue(Notes.of(stringNote)) == stringNote
    }

    def 'maps string to Notes class'() {
        expect:
        standardValueMapper.toNotes(stringNote) == Notes.of(stringNote)
    }
}
