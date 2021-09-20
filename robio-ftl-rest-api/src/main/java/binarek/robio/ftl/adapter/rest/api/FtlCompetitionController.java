package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.CompetitionAppService;
import binarek.robio.ftl.adapter.rest.api.dto.CompetitionDto;
import binarek.robio.ftl.adapter.rest.api.dto.CompetitionRulesDto;
import binarek.robio.ftl.adapter.rest.api.dto.InitializeCompetitionCommandDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/ftl/competitions")
@Validated
@Tag(name = "competitions")
class FtlCompetitionController {

    private final CompetitionAppService competitionAppService;
    private final FtlCompetitionDtoMapper competitionDtoMapper;

    FtlCompetitionController(CompetitionAppService competitionAppService,
                             FtlCompetitionDtoMapper competitionDtoMapper) {
        this.competitionAppService = competitionAppService;
        this.competitionDtoMapper = competitionDtoMapper;
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

    private CompetitionDto getCompetitionDto(UUID competitionId) {
        final var query = competitionDtoMapper.toCompetitionByIdQuery(competitionId);
        return competitionDtoMapper.toCompetitionDto(competitionAppService.getCompetition(query));
    }
}
