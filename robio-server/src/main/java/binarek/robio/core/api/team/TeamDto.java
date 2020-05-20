package binarek.robio.core.api.team;

import binarek.robio.common.domain.value.Notes;
import binarek.robio.core.domain.team.Team;
import binarek.robio.core.domain.team.TeamMember;
import binarek.robio.core.domain.team.TeamName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.UUID;

@Schema(name = Team.ENTITY_NAME)
@Value.Immutable
@JsonDeserialize(as = ImmutableTeamDto.class)
public interface TeamDto {

    @Nullable
    UUID getId();

    @Nullable
    Long getVersion();

    @Nullable
    Notes getNotes();

    @Nullable
    TeamName getName();

    @Nullable
    List<TeamMember> getMembers();

    @Nullable
    List<UUID> getRobotIds();
}
