package binarek.robio.ftl.api.execution.command;

import binarek.robio.common.codegen.ValueDefStyle;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.UUID;

@Value.Immutable
@ValueDefStyle
interface CreateFtlRunCommandDef {

    UUID competitionId();

    UUID robotId();

    boolean passed();

    @Nullable
    BigDecimal timeInSeconds();
}
