package binarek.robio.ftl.adapter.persistence;

import binarek.robio.shared.DateTimeProvider;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Component
class SharedPersistenceMapper {

    private final DateTimeProvider dateTimeProvider;

    SharedPersistenceMapper(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Nullable
    ZonedDateTime toZonedDate(@Nullable LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime
                .atOffset(ZoneOffset.UTC)
                .toZonedDateTime()
                .withZoneSameInstant(dateTimeProvider.getBusinessZoneId());
    }

    @Nullable
    LocalDateTime toLocalDate(@Nullable ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) {
            return null;
        }
        return zonedDateTime
                .withZoneSameInstant(ZoneOffset.UTC)
                .toLocalDateTime();
    }
}
