package binarek.robio.ftl.planning.model;

import binarek.robio.ftl.planning.view.RobotPlaceholderView;
import binarek.robio.shared.model.RobotId;
import binarek.robio.shared.model.TeamId;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
@ValueDefStyle
interface RobotPlaceholderDef extends RobotPlaceholderView {

    @Nullable
    @Value.Parameter
    RobotId getRobotId();

    @Value.Parameter
    TeamId getTeamId();

    default boolean isReady() {
        return getRobotId() != null;
    }
}
