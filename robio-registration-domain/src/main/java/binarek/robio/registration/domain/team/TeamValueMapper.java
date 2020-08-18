package binarek.robio.registration.domain.team;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static binarek.robio.common.util.MapperUtil.mapNullSafe;

@Component
public class TeamValueMapper {

    @Nullable
    public TeamId toTeamId(@Nullable UUID id) {
        return mapNullSafe(id, TeamId::of);
    }

    @Nullable
    public UUID toValue(@Nullable TeamId id) {
        return mapNullSafe(id, TeamId::getValue);
    }

    @Nullable
    public TeamName toTeamName(@Nullable String name) {
        return mapNullSafe(name, TeamName::of);
    }

    @Nullable
    public String toValue(@Nullable TeamName name) {
        return mapNullSafe(name, TeamName::getValue);
    }
}
