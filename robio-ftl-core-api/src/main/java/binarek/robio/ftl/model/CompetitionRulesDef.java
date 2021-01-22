package binarek.robio.ftl.model;

import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
@ValueDefStyle
interface CompetitionRulesDef {

    @Nullable
    Integer getRunsLimitPerRobot();
}
