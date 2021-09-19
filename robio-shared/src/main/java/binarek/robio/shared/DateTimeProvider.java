package binarek.robio.shared;

import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class DateTimeProvider {

    private static final ZoneId BUSINESS_ZONE_ID = ZoneId.of("Europe/Warsaw");

    public ZonedDateTime currentZonedDateTime() {
        return ZonedDateTime.now(BUSINESS_ZONE_ID);
    }

    public ZoneId getBusinessZoneId() {
        return BUSINESS_ZONE_ID;
    }
}
