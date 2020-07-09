package binarek.robio.rest.api.team;

import binarek.robio.domain.common.value.Notes;
import binarek.robio.domain.team.TeamMember;
import binarek.robio.domain.team.TeamName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.UUID;

@Schema(name = "Team")
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
