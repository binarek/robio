package binarek.robio.ftl.planning.api.query;

import binarek.robio.common.codegen.ValueDefStyle;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.UUID;

@Value.Immutable
@ValueDefStyle
interface CompetitionPlanViewDef {

    UUID competitionId();

    List<RobotPlaceholderView> robots();

    @Nullable
    Integer runsLimitPerRobot();
}
