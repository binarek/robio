package binarek.robio.shared;

import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;
import binarek.robio.shared.model.TeamId;
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
    public RobotId toRobotId(@Nullable UUID value) {
        return mapNullSafe(value, RobotId::of);
    }

    @Nullable
    public UUID toValue(@Nullable RobotId id) {
        return mapNullSafe(id, RobotId::getValue);
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
