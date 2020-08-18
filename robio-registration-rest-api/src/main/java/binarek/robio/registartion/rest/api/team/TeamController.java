package binarek.robio.registartion.rest.api.team;

import binarek.robio.registration.domain.team.TeamId;
import binarek.robio.registration.domain.team.TeamService;
import binarek.robio.registartion.rest.api.common.DetailsLevel;
import org.springframework.web.bind.annotation.*;

import static binarek.robio.registartion.rest.api.common.ApiUtil.DEFAULT_DETAILS_LEVEL;
import static binarek.robio.registartion.rest.api.common.ApiUtil.validateEntityPutRequest;

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
            return teamDtoMapper.toTeamDto(teamService.getTeam(id), teamService.getTeamRobotsIds(id));
        } else {
            return teamDtoMapper.toTeamDto(teamService.getTeam(id), null);
        }
    }

    @PostMapping
    public TeamDto postTeam(@RequestBody TeamDto teamDto) {
        return teamDtoMapper.toTeamDto(teamService.createTeam(teamDtoMapper.toTeam(teamDto)));
    }

    @PutMapping("/{id}")
    public TeamDto putTeam(@PathVariable TeamId id, @RequestBody TeamDto teamDto) {
        var team = teamDtoMapper.toTeam(teamDto, id);
        validateEntityPutRequest(id, team.getId());
        return teamDtoMapper.toTeamDto(teamService.saveTeam(team));
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable TeamId id) {
        teamService.deleteTeam(id);
    }
}
