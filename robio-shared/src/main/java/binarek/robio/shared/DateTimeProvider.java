package binarek.robio.shared;

import binarek.robio.shared.configuration.SharedProperties;
import binarek.robio.shared.model.BusinessDateTime;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class DateTimeProvider {

    private final ZoneId businessZoneId;

    DateTimeProvider(SharedProperties sharedProperties) {
        this.businessZoneId = sharedProperties.getTimezone();
    }

    public ZoneId getBusinessZoneId() {
        return businessZoneId;
    }

    public BusinessDateTime currentBusinessDateTime() {
        return BusinessDateTime.of(ZonedDateTime.now(businessZoneId).truncatedTo(ChronoUnit.SECONDS));
    }

    public Instant currentInstant() {
        return Instant.now();
    }
}
