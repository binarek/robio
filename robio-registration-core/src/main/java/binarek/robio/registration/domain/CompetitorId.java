package binarek.robio.registration.domain;

import binarek.robio.common.codegen.ValueTypeStyle;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = ImmutableCompetitorId.class)
public abstract class CompetitorId {

    CompetitorId() {
    }

    public static CompetitorId of(UUID value) {
        return ImmutableCompetitorId.ofValue(value);
    }

    @JsonValue
    @Value.Parameter
    public abstract UUID getValue();
}
