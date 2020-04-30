package binarek.robio.core.api.team;

import binarek.robio.common.domain.DomainEntityDetailsLevel;
import binarek.robio.core.domain.team.Team;
import binarek.robio.core.domain.team.TeamService;
import binarek.robio.core.domain.team.TeamWithAssociations;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static binarek.robio.common.api.ApiUtil.DEFAULT_DETAILS_LEVEL;
import static binarek.robio.common.api.ApiUtil.validateDomainEntityPutRequest;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/{id}")
    public TeamWithAssociations getTeam(@PathVariable UUID id,
                                        @RequestParam(defaultValue = DEFAULT_DETAILS_LEVEL) DomainEntityDetailsLevel detailsLevel) {
        return teamService.getTeam(id, detailsLevel, TeamWithAssociations.class);
    }

    @PostMapping
    public Team postTeam(@RequestBody Team team) {
        return teamService.createTeam(team);
    }

    @PutMapping("/{id}")
    public Team putTeam(@PathVariable UUID id, @RequestBody Team team) {
        validateDomainEntityPutRequest(id, team);
        return teamService.saveTeam(team);
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable UUID id) {
        teamService.deleteTeam(id);
    }
}
