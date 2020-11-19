package binarek.robio.ftl.planning.domain.model;

import binarek.robio.common.codegen.BaseStyle;
import binarek.robio.ftl.planning.api.query.CompetitionPlanView;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.UUID;

@Value.Immutable
@BaseStyle
@SuppressWarnings("immutables:from")    // NOTE: do not use .from(CompetitionPlanView) - this should never happen
public abstract class CompetitionPlan implements CompetitionPlanView {

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
        return ImmutableCompetitionPlan.builder()
                .from(this)
                .addRobots(robots)
                .build();
    }

    public CompetitionPlan changeRunsLimitPerRobot(@Nullable Integer runsLimitPerRobot) {
        return ImmutableCompetitionPlan.builder()
                .from(this)
                .runsLimitPerRobot(runsLimitPerRobot)
                .build();
    }
}
