package binarek.robio.ftl.api.rest;

import binarek.robio.ftl.api.planning.FtlCompetitionPlanAppService;
import binarek.robio.ftl.api.planning.model.FtlCompetitionPlan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/ftl/plans")
public class FtlPlanController {

    private final FtlCompetitionPlanAppService ftlCompetitionPlanAppService;

    public FtlPlanController(FtlCompetitionPlanAppService ftlCompetitionPlanAppService) {
        this.ftlCompetitionPlanAppService = ftlCompetitionPlanAppService;
    }

    @GetMapping("/{competitionId}")
    public FtlCompetitionPlan getPlan(@PathVariable UUID competitionId) {
        return ftlCompetitionPlanAppService.getPlan(competitionId);
    }
}
