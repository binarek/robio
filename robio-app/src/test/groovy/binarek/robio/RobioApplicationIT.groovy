package binarek.robio

import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
@AutoConfigureEmbeddedDatabase
class RobioApplicationIT extends Specification {

    def 'context loads'() {
        expect:
        true
    }
}
