package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.CompetitionPlanAppService;
import binarek.robio.ftl.adapter.rest.api.dto.CompetitionPlanDto;
import binarek.robio.ftl.adapter.rest.api.dto.CompetitionRulesDto;
import binarek.robio.ftl.adapter.rest.api.dto.InitializeCompetitionPlanCommandDto;
import binarek.robio.ftl.adapter.rest.api.dto.RobotDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ftl/competition-plans")
@Validated
class FtlCompetitionPlanController {

    private final CompetitionPlanAppService competitionPlanAppService;
    private final FtlCompetitionPlanDtoMapper planDtoMapper;
    private final FtlRobotDtoMapper robotDtoMapper;

    FtlCompetitionPlanController(CompetitionPlanAppService competitionPlanAppService,
                                 FtlCompetitionPlanDtoMapper planDtoMapper,
                                 FtlRobotDtoMapper robotDtoMapper) {
        this.competitionPlanAppService = competitionPlanAppService;
        this.planDtoMapper = planDtoMapper;
        this.robotDtoMapper = robotDtoMapper;
    }

    @PostMapping
    @Valid
    CompetitionPlanDto initializePlan(@RequestBody @Valid InitializeCompetitionPlanCommandDto commandDto) {
        final var command = planDtoMapper.toInitializeCompetitionPlanCommand(commandDto);
        competitionPlanAppService.initializePlan(command);
        return getCompetitionPlanDto(commandDto.getCompetitionId());
    }

    @GetMapping("/{competitionId}")
    @Valid
    CompetitionPlanDto getPlan(@PathVariable UUID competitionId) {
        return getCompetitionPlanDto(competitionId);
    }

    @GetMapping("/{competitionId}/robots")
    @Valid
    List<@Valid RobotDto> getPlanRobots(@PathVariable UUID competitionId) {
        final var command = planDtoMapper.toSearchCompetitionPlanCommand(competitionId);
        return robotDtoMapper.toRobotDtos(competitionPlanAppService.getPlanRobots(command));
    }

    @PutMapping("/{competitionId}/rules")
    void changePlanRules(@PathVariable UUID competitionId, @RequestBody @Valid CompetitionRulesDto competitionRulesDto) {
        final var command = planDtoMapper.toChangePlanRulesCommand(competitionId, competitionRulesDto);
        competitionPlanAppService.changePlanRules(command);
    }

    private CompetitionPlanDto getCompetitionPlanDto(UUID competitionId) {
        final var command = planDtoMapper.toSearchCompetitionPlanCommand(competitionId);
        return planDtoMapper.toCompetitionPlanDto(competitionPlanAppService.getPlan(command));
    }
}
