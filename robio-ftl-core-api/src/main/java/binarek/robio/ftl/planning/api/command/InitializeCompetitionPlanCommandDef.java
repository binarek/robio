package binarek.robio.ftl.planning.api.command;

import binarek.robio.common.codegen.ValueDefStyle;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.UUID;

@Value.Immutable
@ValueDefStyle
interface InitializeCompetitionPlanCommandDef {

    UUID getCompetitionId();

    @Nullable
    Integer getRunsLimitPerRobot();

    @Value.Check
    default void validate() {
        var runsLimitPerRobot = getRunsLimitPerRobot();
        Assert.state(runsLimitPerRobot == null || runsLimitPerRobot > 0, "Runs limit per robot must be empty or grater than 0");
    }
}
