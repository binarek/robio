package binarek.robio.shared;

import binarek.robio.shared.configuration.SharedProperties;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class DateTimeProvider {

    private final ZoneId businessZoneId;

    DateTimeProvider(SharedProperties sharedProperties) {
        this.businessZoneId = sharedProperties.getTimezone();
    }

    public ZonedDateTime currentZonedDateTime() {
        return ZonedDateTime.now(businessZoneId);
    }

    public Instant currentInstant() {
        return Instant.now();
    }

    public ZoneId getBusinessZoneId() {
        return businessZoneId;
    }
}
