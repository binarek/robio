package binarek.robio.registartion.rest.api.team;

import binarek.robio.common.codegen.BaseMapperConfig;
import binarek.robio.registration.domain.robot.RobotId;
import binarek.robio.registration.domain.robot.RobotValueMapper;
import binarek.robio.registration.domain.team.Team;
import binarek.robio.registration.domain.team.TeamBasicInfo;
import binarek.robio.registration.domain.team.TeamId;
import binarek.robio.registration.domain.team.TeamValueMapper;
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
