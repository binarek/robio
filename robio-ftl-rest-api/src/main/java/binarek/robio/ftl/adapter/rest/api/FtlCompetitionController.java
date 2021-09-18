package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.CompetitionAppService;
import binarek.robio.ftl.adapter.rest.api.dto.CompetitionDto;
import binarek.robio.ftl.adapter.rest.api.dto.StartCompetitionCommandDto;
import binarek.robio.ftl.command.SearchCompetitionCommand;
import binarek.robio.shared.model.CompetitionId;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/ftl/competitions")
@Validated
class FtlCompetitionController {

    private final CompetitionAppService competitionAppService;
    private final FtlCompetitionDtoMapper dtoMapper;

    FtlCompetitionController(CompetitionAppService competitionAppService, FtlCompetitionDtoMapper dtoMapper) {
        this.competitionAppService = competitionAppService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping
    @Valid CompetitionDto startCompetition(@RequestBody @Valid StartCompetitionCommandDto commandDto) {
        var command = dtoMapper.toStartCompetitionCommand(commandDto);
        competitionAppService.startCompetition(command);
        return getCompetitionDto(command.getCompetitionId());
    }

    @GetMapping("/{competitionId}")
    @Valid CompetitionDto getCompetition(@PathVariable UUID competitionId) {
        return getCompetitionDto(CompetitionId.of(competitionId));
    }

    private CompetitionDto getCompetitionDto(CompetitionId competitionId) {
        return dtoMapper.toCompetitionDto(competitionAppService.getCompetition(SearchCompetitionCommand.of(competitionId)));
    }
}
