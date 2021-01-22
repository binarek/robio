package binarek.robio.ftl.planning.model;

import binarek.robio.common.codegen.ValueDefStyle;
import binarek.robio.ftl.planning.view.RobotPlaceholderView;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Value.Immutable
@ValueDefStyle
interface RobotPlaceholderDef extends RobotPlaceholderView {

    @Nullable
    @Value.Parameter
    UUID getRobotId();

    @Value.Parameter
    UUID getTeamId();

    default boolean isReady() {
        return getRobotId() != null;
    }
}
