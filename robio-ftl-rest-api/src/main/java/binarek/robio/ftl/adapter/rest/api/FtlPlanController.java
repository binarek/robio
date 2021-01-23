package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.adapter.rest.api.dto.CompetitionPlanDto;
import binarek.robio.ftl.adapter.rest.api.dto.CompetitionRulesDto;
import binarek.robio.ftl.adapter.rest.api.dto.InitializeCompetitionPlanCommandDto;
import binarek.robio.ftl.planning.CompetitionPlanAppService;
import binarek.robio.ftl.planning.command.SearchCompetitionPlanCommand;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/ftl/plans")
@Validated
public class FtlPlanController {

    private final CompetitionPlanAppService competitionPlanAppService;
    private final FtlPlanDtoMapper dtoMapper;

    public FtlPlanController(CompetitionPlanAppService competitionPlanAppService, FtlPlanDtoMapper dtoMapper) {
        this.competitionPlanAppService = competitionPlanAppService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping
    public @Valid CompetitionPlanDto initializePlan(@RequestBody @Valid InitializeCompetitionPlanCommandDto commandDto) {
        var command = dtoMapper.toInitializeCompetitionPlanCommand(commandDto);
        competitionPlanAppService.initializePlan(command);
        return dtoMapper.toCompetitionPlanDto(competitionPlanAppService.getPlan(SearchCompetitionPlanCommand.of(command.getCompetitionId())));
    }

    @GetMapping("/{competitionId}")
    public @Valid CompetitionPlanDto getPlan(@PathVariable UUID competitionId) {
        return dtoMapper.toCompetitionPlanDto(competitionPlanAppService.getPlan(dtoMapper.toSearchCompetitionPlanCommand(competitionId)));
    }

    @PutMapping("/{competitionId}/rules")
    public void changePlanRules(@PathVariable UUID competitionId, @RequestBody @Valid CompetitionRulesDto competitionRulesDto) {
        competitionPlanAppService.changePlanRules(dtoMapper.toChangePlanRulesCommand(competitionId, competitionRulesDto));
    }
}
