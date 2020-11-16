package binarek.robio.ftl.planning.rest.api;

import binarek.robio.ftl.planning.api.CompetitionPlanAppService;
import binarek.robio.ftl.planning.api.command.InitializeCompetitionPlanCommand;
import binarek.robio.ftl.planning.api.query.CompetitionPlanView;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/ftl/plans")
public class FtlPlansController {

    private final CompetitionPlanAppService competitionPlanAppService;

    public FtlPlansController(CompetitionPlanAppService competitionPlanAppService) {
        this.competitionPlanAppService = competitionPlanAppService;
    }

    @PostMapping
    public CompetitionPlanView postPlan(@RequestBody InitializeCompetitionPlanCommand command) {
        competitionPlanAppService.initializePlan(command);
        return competitionPlanAppService.getPlan(command.competitionId());
    }

    @GetMapping("/{competitionId}")
    public CompetitionPlanView getPlan(@PathVariable UUID competitionId) {
        return competitionPlanAppService.getPlan(competitionId);
    }
}
