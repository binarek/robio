package binarek.robio.ftl.api.execution.command;

import binarek.robio.common.codegen.ValueDefStyle;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@ValueDefStyle
interface DeleteFtlRunCommandDef {

    UUID competitionId();

    UUID robotId();

    Integer number();
}
