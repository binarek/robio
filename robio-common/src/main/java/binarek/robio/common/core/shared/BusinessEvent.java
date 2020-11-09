package binarek.robio.common.core.shared;

import java.time.Instant;
import java.util.UUID;

public interface BusinessEvent {

    UUID id();

    Instant timestamp();
}
