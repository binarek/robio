package binarek.robio.ftl.domain.planning.model;

import binarek.robio.common.codegen.BaseStyle;
import binarek.robio.ftl.domain.execution.model.FtlCompetition;
import binarek.robio.ftl.domain.execution.model.FtlParticipationDetails;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static binarek.robio.ftl.domain.execution.model.FtlParticipationDetails.QualificationStatus.QUALIFIED;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableFtlCompetitionPlan.class)
public abstract class FtlCompetitionPlan {

    public abstract UUID competitionId();

    @Nullable
    public abstract Long version();

    public abstract List<FtlRobotPlaceholder> robots();

    @Nullable
    public abstract Integer runsLimitPerRobot();

    public final boolean canBeApplied() {
        return robots().stream().allMatch(FtlRobotPlaceholder::ready);
    }

    public final FtlCompetitionPlan addRobot(FtlRobotPlaceholder robot) {
        return ImmutableFtlCompetitionPlan.builder().from(this)
                .addRobots(robot)
                .build();
    }

    public FtlCompetitionPlan changeRunsLimitPerRobot(@Nullable Integer runsLimitPerRobot) {
        return ImmutableFtlCompetitionPlan.builder().from(this)
                .runsLimitPerRobot(runsLimitPerRobot)
                .build();
    }

    public final FtlCompetition apply() {
        if (!canBeApplied()) {
            // TODO throw exception
        }
        var participationDetails = robots().stream()
                .map(this::buildParticipation)
                .collect(Collectors.toList());
        return FtlCompetition.newFtlCompetition(competitionId(), participationDetails, runsLimitPerRobot());
    }

    public static FtlCompetitionPlan newPlan(UUID competitionId, @Nullable Integer runsLimitPerRobot) {
        return ImmutableFtlCompetitionPlan.builder()
                .competitionId(competitionId)
                .runsLimitPerRobot(runsLimitPerRobot)
                .build();
    }

    private FtlParticipationDetails buildParticipation(FtlRobotPlaceholder robot) {
        var robotId = robot.robotId();
        Assert.notNull(robotId, "Cannot build participation for robot: " + robot);
        return FtlParticipationDetails.newFtlParticipationDetails(robotId, QUALIFIED);
    }
}
