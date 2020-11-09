package binarek.robio.ftl.api.planning.model;

import binarek.robio.common.codegen.ValueDefStyle;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.UUID;

@Value.Immutable
@ValueDefStyle
interface FtlCompetitionPlanDef {

    UUID competitionId();

    List<FtlRobotPlaceholderDef> robots();

    @Nullable
    Integer runsLimitPerRobot();
}
