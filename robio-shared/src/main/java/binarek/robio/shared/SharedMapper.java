package binarek.robio.shared;

import binarek.robio.shared.model.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static binarek.robio.util.MapperUtil.mapNullSafe;

@Component
public class SharedMapper {

    @Nullable
    public CompetitionId toCompetitionId(@Nullable UUID value) {
        return mapNullSafe(value, CompetitionId::of);
    }

    @Nullable
    public UUID toValue(@Nullable CompetitionId id) {
        return mapNullSafe(id, CompetitionId::getValue);
    }

    @Nullable
    public CompetitorId toCompetitorId(@Nullable UUID value) {
        return mapNullSafe(value, CompetitorId::of);
    }

    @Nullable
    public UUID toValue(@Nullable CompetitorId id) {
        return mapNullSafe(id, CompetitorId::getValue);
    }

    @Nullable
    public RobotId toRobotId(@Nullable UUID value) {
        return mapNullSafe(value, RobotId::of);
    }

    @Nullable
    public UUID toValue(@Nullable RobotId id) {
        return mapNullSafe(id, RobotId::getValue);
    }

    @Nullable
    public RobotName toRobotName(@Nullable String value) {
        return mapNullSafe(value, RobotName::of);
    }

    @Nullable
    public String toValue(@Nullable RobotName name) {
        return mapNullSafe(name, RobotName::getValue);
    }

    @Nullable
    public TeamId toTeamId(@Nullable UUID value) {
        return mapNullSafe(value, TeamId::of);
    }

    @Nullable
    public UUID toValue(@Nullable TeamId id) {
        return mapNullSafe(id, TeamId::getValue);
    }
}
