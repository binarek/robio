package binarek.robio.core.api.team;

import binarek.robio.common.api.DetailsLevel;
import binarek.robio.core.domain.team.Team;
import binarek.robio.core.domain.team.TeamFetchProperties;
import binarek.robio.core.domain.team.TeamService;
import binarek.robio.core.domain.team.TeamSortableField;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static binarek.robio.common.api.ApiUtil.*;
import static binarek.robio.core.domain.team.TeamFetchProperties.DetailsLevel.TEAM;
import static binarek.robio.core.domain.team.TeamFetchProperties.DetailsLevel.TEAM_BASIC_INFO;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;
    private final TeamDtoMapper teamDtoMapper;

    public TeamController(TeamService teamService, TeamDtoMapper teamDtoMapper) {
        this.teamService = teamService;
        this.teamDtoMapper = teamDtoMapper;
    }

    @GetMapping
    public List<TeamDto> getTeams(@RequestParam(defaultValue = DEFAULT_LIMIT) int limit,
                                  @RequestParam(defaultValue = DEFAULT_OFFSET) int offset,
                                  @RequestParam(defaultValue = "name") List<String> sort,
                                  @RequestParam(defaultValue = DEFAULT_DETAILS_LEVEL) DetailsLevel detailsLevel) {
        var teams = teamService.getTeams(buildTeamFetchProperties(limit, offset, sort, detailsLevel));
        if (detailsLevel != DetailsLevel.FULL) {
            return teams.stream()
                    .map(teamDtoMapper::toTeamDto)
                    .collect(Collectors.toUnmodifiableList());
        } else {
            var teamIds = teams.stream()
                    .map(Team::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toUnmodifiableList());
            var robotIds = teamService.getTeamRobotsIds(teamIds);
            return teams.stream()
                    .map(team -> teamDtoMapper.toTeamDto(team, robotIds.get(team.getId())))
                    .collect(Collectors.toUnmodifiableList());
        }
    }

    @GetMapping("/{id}")
    public TeamDto getTeam(@PathVariable UUID id,
                           @RequestParam(defaultValue = DEFAULT_DETAILS_LEVEL) DetailsLevel detailsLevel) {
        if (detailsLevel == DetailsLevel.BASIC) {
            return teamDtoMapper.toTeamDto(teamService.getTeamBasicInfo(id));
        } else {
            return teamDtoMapper.toTeamDto(
                    teamService.getTeam(id),
                    detailsLevel == DetailsLevel.FULL ? teamService.getTeamRobotsIds(id) : null);
        }
    }

    @PostMapping
    public TeamDto postTeam(@RequestBody TeamDto teamDto) {
        return teamDtoMapper.toTeamDto(teamService.createTeam(teamDtoMapper.toTeam(teamDto)));
    }

    @PutMapping("/{id}")
    public TeamDto putTeam(@PathVariable UUID id, @RequestBody TeamDto teamDto) {
        var team = teamDtoMapper.toTeam(teamDto, id);
        validateEntityPutRequest(id, team); // TODO remove?
        return teamDtoMapper.toTeamDto(teamService.saveTeam(team));
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable UUID id) {
        teamService.deleteTeam(id);
    }

    private static TeamFetchProperties buildTeamFetchProperties(int limit, int offset, List<String> sort,
                                                                DetailsLevel detailsLevel) {
        return TeamFetchProperties.builder()
                .limit(limit)
                .offset(offset)
                .sort(sort.stream().map(TeamSortableField::fromFieldName).collect(Collectors.toUnmodifiableList()))
                .detailsLevel(detailsLevel == DetailsLevel.BASIC ? TEAM_BASIC_INFO : TEAM)
                .build();
    }
}
