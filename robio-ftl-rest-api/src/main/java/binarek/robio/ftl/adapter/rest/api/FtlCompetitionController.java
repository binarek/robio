package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.CompetitionAppService;
import binarek.robio.ftl.ScoreboardAppService;
import binarek.robio.ftl.adapter.rest.api.dto.CompetitionDto;
import binarek.robio.ftl.adapter.rest.api.dto.CompetitionRulesDto;
import binarek.robio.ftl.adapter.rest.api.dto.InitializeCompetitionCommandDto;
import binarek.robio.ftl.adapter.rest.api.dto.ScoreboardDto;
import binarek.robio.shared.model.CompetitionId;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/ftl/competitions")
@Tag(name = "competitions")
@SecurityRequirement(name = "bearerAuth")
class FtlCompetitionController {

    private final CompetitionAppService competitionAppService;
    private final ScoreboardAppService scoreboardAppService;
    private final FtlCompetitionDtoMapper competitionDtoMapper;
    private final FtlScoreboardDtoMapper scoreboardDtoMapper;

    FtlCompetitionController(CompetitionAppService competitionAppService,
                             ScoreboardAppService scoreboardAppService,
                             FtlCompetitionDtoMapper competitionDtoMapper,
                             FtlScoreboardDtoMapper scoreboardDtoMapper) {
        this.competitionAppService = competitionAppService;
        this.scoreboardAppService = scoreboardAppService;
        this.competitionDtoMapper = competitionDtoMapper;
        this.scoreboardDtoMapper = scoreboardDtoMapper;
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
    @Valid
    CompetitionRulesDto changeCompetitionRules(@PathVariable UUID competitionId, @RequestBody @Valid CompetitionRulesDto competitionRulesDto) {
        final var command = competitionDtoMapper.toChangeCompetitionRulesCommand(competitionId, competitionRulesDto);
        competitionAppService.changeCompetitionRules(command);
        return getCompetitionDto(competitionId).getRules();
    }

    @GetMapping("/{competitionId}")
    @Valid
    CompetitionDto getCompetition(@PathVariable UUID competitionId) {
        return getCompetitionDto(competitionId);
    }

    @GetMapping("/{competitionId}/scoreboard")
    @Valid
    ScoreboardDto getScoreboard(@PathVariable UUID competitionId) {
        return scoreboardDtoMapper.toScoreboardDto(scoreboardAppService.getScoreboard(CompetitionId.of(competitionId)));
    }

    private CompetitionDto getCompetitionDto(UUID competitionId) {
        final var query = competitionDtoMapper.toCompetitionByIdQuery(competitionId);
        return competitionDtoMapper.toCompetitionDto(competitionAppService.getCompetition(query));
    }
}
