package binarek.robio.ftl.planning.model;

import binarek.robio.common.codegen.ValueDefStyle;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
@ValueDefStyle
interface CompetitionRulesDef {

    @Nullable
    Integer getRunsLimitPerRobot();
}
