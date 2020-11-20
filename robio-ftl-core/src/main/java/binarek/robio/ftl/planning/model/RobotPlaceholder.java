package binarek.robio.ftl.planning.model;

import binarek.robio.common.codegen.ValueTypeStyle;
import binarek.robio.ftl.planning.view.RobotPlaceholderView;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Value.Immutable
@ValueTypeStyle
public abstract class RobotPlaceholder implements RobotPlaceholderView {

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
