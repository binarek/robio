package binarek.robio.ftl.domain.execution.model;

import binarek.robio.common.codegen.ValueTypeStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = ImmutableFtlParticipation.class)
public abstract class FtlParticipation {

    FtlParticipation() {
    }

    @Value.Parameter
    public abstract UUID competitionId();

    @Value.Parameter
    public abstract UUID robotId();

    public static FtlParticipation of(UUID competitionId, UUID robotId) {
        return ImmutableFtlParticipation.ofValue(competitionId, robotId);
    }
}
