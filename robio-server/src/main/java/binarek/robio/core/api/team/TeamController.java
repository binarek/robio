package binarek.robio.core.api.team;

import binarek.robio.common.api.DetailsLevel;
import binarek.robio.core.domain.robot.RobotId;
import binarek.robio.core.domain.team.TeamId;
import binarek.robio.core.domain.team.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

import static binarek.robio.common.api.ApiUtil.DEFAULT_DETAILS_LEVEL;
import static binarek.robio.common.api.ApiUtil.validateEntityPutRequest;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;
    private final TeamDtoMapper teamDtoMapper;

    public TeamController(TeamService teamService, TeamDtoMapper teamDtoMapper) {
        this.teamService = teamService;
        this.teamDtoMapper = teamDtoMapper;
    }

    @GetMapping("/{id}")
    public TeamDto getTeam(@PathVariable TeamId id,
                           @RequestParam(defaultValue = DEFAULT_DETAILS_LEVEL) DetailsLevel detailsLevel) {
        if (detailsLevel == DetailsLevel.BASIC) {
            return teamDtoMapper.toTeamDto(teamService.getTeamBasicInfo(id));
        } else if (detailsLevel == DetailsLevel.FULL) {
            var robotIds = teamService.getTeamRobotsIds(id).stream()
                    .map(RobotId::getValue)
                    .collect(Collectors.toUnmodifiableList());
            return teamDtoMapper.toTeamDto(teamService.getTeam(id), robotIds);
        } else {
            return teamDtoMapper.toTeamDto(teamService.getTeam(id), null);
        }
    }

    @PostMapping
    public TeamDto postTeam(@RequestBody TeamDto teamDto) {
        return teamDtoMapper.toTeamDto(teamService.createTeam(teamDtoMapper.toTeam(teamDto)));
    }

    @PutMapping("/{id}")
    public TeamDto putTeam(@PathVariable UUID id, @RequestBody TeamDto teamDto) {
        var team = teamDtoMapper.toTeam(teamDto, id);
        validateEntityPutRequest(id, team.getId());
        return teamDtoMapper.toTeamDto(teamService.saveTeam(team));
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable TeamId id) {
        teamService.deleteTeam(id);
    }
}
