package binarek.robio.ftl.planning.domain.model;

import binarek.robio.common.codegen.BaseStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.UUID;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableCompetitionPlan.class)
public abstract class CompetitionPlan {

    CompetitionPlan() {
    }

    public abstract UUID getCompetitionId();

    @Nullable
    public abstract Long getVersion();

    public abstract List<RobotPlaceholder> getRobots();

    @Nullable
    public abstract Integer getRunsLimitPerRobot();

    public final boolean canBeApplied() {
        return getRobots().stream().allMatch(RobotPlaceholder::isReady);
    }

    public static CompetitionPlan newPlan(UUID competitionId, @Nullable Integer runsLimitPerRobot) {
        return ImmutableCompetitionPlan.builder()
                .competitionId(competitionId)
                .runsLimitPerRobot(runsLimitPerRobot)
                .build();
    }

    public final CompetitionPlan addRobots(RobotPlaceholder... robots) {
        return ImmutableCompetitionPlan.builder().from(this)
                .addRobots(robots)
                .build();
    }

    public CompetitionPlan changeRunsLimitPerRobot(@Nullable Integer runsLimitPerRobot) {
        return ImmutableCompetitionPlan.builder().from(this)
                .runsLimitPerRobot(runsLimitPerRobot)
                .build();
    }
}
