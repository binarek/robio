package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.CompetitionPlanAppService;
import binarek.robio.ftl.adapter.rest.api.dto.CompetitionPlanDto;
import binarek.robio.ftl.adapter.rest.api.dto.CompetitionRulesDto;
import binarek.robio.ftl.adapter.rest.api.dto.InitializeCompetitionPlanCommandDto;
import binarek.robio.ftl.command.SearchCompetitionPlanCommand;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/ftl/competition-plans")
@Validated
class FtlCompetitionPlanController {

    private final CompetitionPlanAppService competitionPlanAppService;
    private final FtlCompetitionPlanDtoMapper dtoMapper;

    FtlCompetitionPlanController(CompetitionPlanAppService competitionPlanAppService, FtlCompetitionPlanDtoMapper dtoMapper) {
        this.competitionPlanAppService = competitionPlanAppService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping
    @Valid CompetitionPlanDto initializePlan(@RequestBody @Valid InitializeCompetitionPlanCommandDto commandDto) {
        var command = dtoMapper.toInitializeCompetitionPlanCommand(commandDto);
        competitionPlanAppService.initializePlan(command);
        return dtoMapper.toCompetitionPlanDto(competitionPlanAppService.getPlan(SearchCompetitionPlanCommand.of(command.getCompetitionId())));
    }

    @GetMapping("/{competitionId}")
    @Valid CompetitionPlanDto getPlan(@PathVariable UUID competitionId) {
        return dtoMapper.toCompetitionPlanDto(competitionPlanAppService.getPlan(dtoMapper.toSearchCompetitionPlanCommand(competitionId)));
    }

    @PutMapping("/{competitionId}/rules")
    void changePlanRules(@PathVariable UUID competitionId, @RequestBody @Valid CompetitionRulesDto competitionRulesDto) {
        competitionPlanAppService.changePlanRules(dtoMapper.toChangePlanRulesCommand(competitionId, competitionRulesDto));
    }
}
