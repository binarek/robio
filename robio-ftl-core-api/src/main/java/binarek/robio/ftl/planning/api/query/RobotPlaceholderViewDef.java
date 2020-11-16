package binarek.robio.ftl.planning.api.query;

import binarek.robio.common.codegen.ValueDefStyle;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Value.Immutable
@ValueDefStyle
interface RobotPlaceholderViewDef {

    UUID teamId();

    @Nullable
    UUID robotId();
}
