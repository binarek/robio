package binarek.robio.core.api.team;

import binarek.robio.codegen.BaseMapperConfig;
import binarek.robio.core.domain.team.Team;
import binarek.robio.core.domain.team.TeamBasicInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.UUID;

@Mapper(config = BaseMapperConfig.class)
public interface TeamDtoMapper {

    Team toTeam(TeamDto dto);

    @Mapping(target = "members", ignore = true)
    @Mapping(target = "robotIds", ignore = true)
    TeamDto toTeamDto(TeamBasicInfo team);

    default TeamDto toTeamDto(Team team) {
        return toTeamDto(team, null);
    }

    TeamDto toTeamDto(Team team, @Nullable List<UUID> robotIds);
}
