package binarek.robio.ftl.planning.domain.model;

import binarek.robio.common.codegen.ValueTypeStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = ImmutableRobotPlaceholder.class)
public abstract class RobotPlaceholder {

    RobotPlaceholder() {
    }

    @Nullable
    @Value.Parameter
    public abstract UUID getRobotId();

    @Value.Parameter
    public abstract UUID getTeamId();

    public final boolean isReady() {
        return getRobotId() != null;
    }

    public static RobotPlaceholder of(@Nullable UUID robotId, UUID teamId) {
        return ImmutableRobotPlaceholder.ofValue(robotId, teamId);
    }
}
