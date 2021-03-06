package binarek.robio.core.domain.team;

import binarek.robio.common.domain.entity.EntityFetchProperties;
import binarek.robio.common.domain.value.SortOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
@JsonDeserialize(as = ImmutableTeamFetchProperties.class)
public abstract class TeamFetchProperties extends EntityFetchProperties<TeamSortableField> {

    TeamFetchProperties() {
    }

    public static Builder builder() {
        return ImmutableTeamFetchProperties.builder();
    }

    public static TeamFetchProperties of(DetailsLevel detailsLevel) {
        return ImmutableTeamFetchProperties.builder().detailsLevel(detailsLevel).build();
    }

    @Nullable
    public abstract DetailsLevel getDetailsLevel();

    public enum DetailsLevel {
        TEAM_BASIC_INFO,
        TEAM,
    }

    public interface Builder {

        Builder limit(@Nullable Integer limit);

        Builder offset(@Nullable Integer offset);

        Builder sort(@Nullable Iterable<? extends SortOrder<TeamSortableField>> sort);

        Builder detailsLevel(@Nullable DetailsLevel detailsLevel);

        TeamFetchProperties build();
    }
}
