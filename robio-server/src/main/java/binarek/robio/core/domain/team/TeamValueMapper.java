package binarek.robio.core.domain.team;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static binarek.robio.common.util.MapperUtil.mapNullSafe;

@Component
public class TeamValueMapper {

    @Nullable
    public TeamName toTeamName(@Nullable String name) {
        return mapNullSafe(name, TeamName::of);
    }

    @Nullable
    public String toValue(@Nullable TeamName name) {
        return mapNullSafe(name, TeamName::getValue);
    }
}
