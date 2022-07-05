package binarek.robio.shared.model

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime;

class BusinessDateTimeTest extends Specification {

    static ZONE_ID = ZoneId.of('Europe/Berlin')

    def 'has precision if seconds'() {
        given:
        def expectedDateTime = ZonedDateTime.of(2022, 11, 21, 12, 56, 27, 0, ZONE_ID)
        when:
        def result = BusinessDateTime.of(expectedDateTime.plusNanos(324523))
        then:
        result.getValue() == expectedDateTime
    }

    @Unroll
    def 'can add days: #initDate + #days days = #expectedDate'() {
        given:
        def initZonedDateTime = ZonedDateTime.of(LocalDateTime.parse(initDate), ZONE_ID)
        def expectedZonedDateTime = ZonedDateTime.of(LocalDateTime.parse(expectedDate), ZONE_ID)
        expect:
        BusinessDateTime.of(initZonedDateTime).plusDays(days) == BusinessDateTime.of(expectedZonedDateTime)
        where:
        initDate              | days || expectedDate
        '2022-03-21T09:11:59' | 0    || '2022-03-21T09:11:59'
        '2022-01-01T12:05:22' | 10   || '2022-01-11T12:05:22'
        '2022-01-01T02:23:24' | -10  || '2021-12-22T02:23:24'
    }
}
