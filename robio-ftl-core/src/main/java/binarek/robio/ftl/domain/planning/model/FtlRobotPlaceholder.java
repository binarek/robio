package binarek.robio.ftl.domain.planning.model;

import binarek.robio.common.codegen.ValueTypeStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = ImmutableFtlRobotPlaceholder.class)
public abstract class FtlRobotPlaceholder {

    FtlRobotPlaceholder() {
    }

    @Nullable
    @Value.Parameter
    public abstract UUID robotId();

    @Value.Parameter
    public abstract UUID teamId();

    public final boolean ready() {
        return robotId() != null;
    }

    public static FtlRobotPlaceholder of(@Nullable UUID robotId, UUID teamId) {
        return ImmutableFtlRobotPlaceholder.ofValue(robotId, teamId);
    }
}
