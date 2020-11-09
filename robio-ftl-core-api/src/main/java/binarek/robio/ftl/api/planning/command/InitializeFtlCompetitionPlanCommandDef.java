package binarek.robio.ftl.api.planning.command;

import binarek.robio.common.codegen.ValueDefStyle;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Value.Immutable
@ValueDefStyle
interface InitializeFtlCompetitionPlanCommandDef {

    UUID competitionId();

    @Nullable
    Integer runsLimitPerRobot();
}
