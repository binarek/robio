package binarek.robio.ftl.api.planning.model;

import binarek.robio.common.codegen.ValueDefStyle;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Value.Immutable
@ValueDefStyle
interface FtlRobotPlaceholderDef {

    UUID teamId();

    @Nullable
    UUID robotId();
}
