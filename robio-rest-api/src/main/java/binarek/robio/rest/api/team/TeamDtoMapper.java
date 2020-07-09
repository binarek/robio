package binarek.robio.rest.api.team;

import binarek.robio.domain.robot.RobotId;
import binarek.robio.domain.robot.RobotValueMapper;
import binarek.robio.domain.team.Team;
import binarek.robio.domain.team.TeamBasicInfo;
import binarek.robio.domain.team.TeamId;
import binarek.robio.domain.team.TeamValueMapper;
import binarek.robio.util.codegen.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.lang.Nullable;

import java.util.List;

@Mapper(config = BaseMapperConfig.class, uses = {TeamValueMapper.class, RobotValueMapper.class})
public interface TeamDtoMapper {

    Team toTeam(TeamDto dto);

    @Mapping(target = "id", source = "teamId")
    Team toTeam(TeamDto dto, TeamId teamId);

    @Mapping(target = "members", ignore = true)
    @Mapping(target = "robotIds", ignore = true)
    TeamDto toTeamDto(TeamBasicInfo team);

    default TeamDto toTeamDto(Team team) {
        return toTeamDto(team, null);
    }

    TeamDto toTeamDto(Team team, @Nullable List<RobotId> robotIds);
}
