package binarek.robio.common.domain.event;

import java.time.Instant;

public interface DomainEvent {

    Instant getOccurredOn();

    static Instant occurredNow() {
        return Instant.now();
    }
}
