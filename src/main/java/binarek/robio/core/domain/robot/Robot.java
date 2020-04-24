package binarek.robio.core.domain.robot;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.UUID;

@Value.Immutable
@JsonDeserialize(as = ImmutableRobot.class)
public interface Robot {

    @Nullable
    UUID getId();

    String getName();

    @Nullable
    String getNotes();

    @Nullable
    BigDecimal getWeight();

    @Nullable
    BigDecimal getWidth();

    @Nullable
    BigDecimal getLength();

    @Nullable
    BigDecimal getHeight();
}
