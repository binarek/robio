package binarek.robio.ftl.planning.rest.api;

import binarek.robio.ftl.planning.api.CompetitionPlanAppService;
import binarek.robio.ftl.planning.rest.api.dto.CompetitionPlanDto;
import binarek.robio.ftl.planning.rest.api.dto.InitializeCompetitionPlanCommandDto;
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
    public @Valid CompetitionPlanDto postPlan(@RequestBody @Valid InitializeCompetitionPlanCommandDto commandDto) {
        var command = dtoMapper.toInitializeCompetitionPlanCommand(commandDto);
        competitionPlanAppService.initializePlan(command);
        return dtoMapper.toCompetitionPlanDto(competitionPlanAppService.getPlan(command.getCompetitionId()));
    }

    @GetMapping("/{competitionId}")
    public @Valid CompetitionPlanDto getPlan(@PathVariable UUID competitionId) {
        return dtoMapper.toCompetitionPlanDto(competitionPlanAppService.getPlan(competitionId));
    }
}
