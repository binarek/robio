package binarek.robio.ftl.domain.execution.model;

import binarek.robio.common.codegen.BaseStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableFtlCompetition.class)
public abstract class FtlCompetition {

    FtlCompetition() {
    }

    public abstract UUID competitionId();

    @Nullable
    public abstract Long getVersion();

    public abstract Map<UUID, FtlParticipationDetails> participationDetailsPerRobot();

    @Nullable
    public abstract Integer runsLimitPerRobot();

    public final FtlCompetition disqualifyRobots(Collection<UUID> robotIdsToDisqualify) {
        var newParticipationDetails = participationDetailsPerRobot().values().stream()
                .map(details -> robotIdsToDisqualify.contains(details.robotId()) ? details.disqualify() : details);

        return of(competitionId(), newParticipationDetails, runsLimitPerRobot());
    }

    public static FtlCompetition newFtlCompetition(UUID competitionId, List<FtlParticipationDetails> participationDetails,
                                                   @Nullable Integer runsLimitPerRobot) {
        return of(competitionId, participationDetails.stream(), runsLimitPerRobot);
    }

    private static FtlCompetition of(UUID competitionId, Stream<FtlParticipationDetails> participationDetails,
                                     @Nullable Integer runsLimitPerRobot) {
        var participationDetailsPerRobot = participationDetails
                .collect(Collectors.toMap(FtlParticipationDetails::robotId, Function.identity()));

        return ImmutableFtlCompetition.builder()
                .competitionId(competitionId)
                .participationDetailsPerRobot(participationDetailsPerRobot)
                .runsLimitPerRobot(runsLimitPerRobot)
                .build();
    }
}
