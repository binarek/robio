package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.CompetitionAppService;
import binarek.robio.ftl.adapter.rest.api.dto.CompetitionDto;
import binarek.robio.ftl.adapter.rest.api.dto.CompetitionRulesDto;
import binarek.robio.ftl.adapter.rest.api.dto.InitializeCompetitionCommandDto;
import binarek.robio.ftl.adapter.rest.api.dto.RobotDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ftl/competitions")
@Validated
class FtlCompetitionController {

    private final CompetitionAppService competitionAppService;
    private final FtlCompetitionDtoMapper competitionDtoMapper;
    private final FtlRobotDtoMapper robotDtoMapper;

    FtlCompetitionController(CompetitionAppService competitionAppService,
                             FtlCompetitionDtoMapper competitionDtoMapper,
                             FtlRobotDtoMapper robotDtoMapper) {
        this.competitionAppService = competitionAppService;
        this.competitionDtoMapper = competitionDtoMapper;
        this.robotDtoMapper = robotDtoMapper;
    }

    @PostMapping
    @Valid
    CompetitionDto initializeCompetition(@RequestBody @Valid InitializeCompetitionCommandDto dto) {
        var command = competitionDtoMapper.toInitializeCompetitionCommand(dto);
        competitionAppService.initializeCompetition(command);
        return getCompetitionDto(dto.getCompetitionId());
    }

    @PostMapping("/{competitionId}:start")
    @Valid
    CompetitionDto startCompetition(@PathVariable UUID competitionId) {
        var command = competitionDtoMapper.toStartCompetitionCommand(competitionId);
        competitionAppService.startCompetition(command);
        return getCompetitionDto(competitionId);
    }

    @PutMapping("/{competitionId}/rules")
    CompetitionDto changeCompetitionRules(@PathVariable UUID competitionId, @RequestBody @Valid CompetitionRulesDto competitionRulesDto) {
        final var command = competitionDtoMapper.toChangeCompetitionRulesCommand(competitionId, competitionRulesDto);
        competitionAppService.changeCompetitionRules(command);
        return getCompetitionDto(competitionId);
    }

    @GetMapping("/{competitionId}")
    @Valid
    CompetitionDto getCompetition(@PathVariable UUID competitionId) {
        return getCompetitionDto(competitionId);
    }

    @GetMapping("/{competitionId}/robots")
    @Valid
    List<@Valid RobotDto> getCompetitionRobots(@PathVariable UUID competitionId) {
        final var command = competitionDtoMapper.toSearchCompetitionCommand(competitionId);
        return robotDtoMapper.toRobotDtos(competitionAppService.getCompetitionRobots(command));
    }

    private CompetitionDto getCompetitionDto(UUID competitionId) {
        final var command = competitionDtoMapper.toSearchCompetitionCommand(competitionId);
        return competitionDtoMapper.toCompetitionDto(competitionAppService.getCompetition(command));
    }
}
