package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.CompetitionAppService;
import binarek.robio.ftl.adapter.rest.api.dto.CompetitionDto;
import binarek.robio.ftl.adapter.rest.api.dto.RobotDto;
import binarek.robio.ftl.adapter.rest.api.dto.StartCompetitionCommandDto;
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
    CompetitionDto startCompetition(@RequestBody @Valid StartCompetitionCommandDto commandDto) {
        var command = competitionDtoMapper.toStartCompetitionCommand(commandDto);
        competitionAppService.startCompetition(command);
        return getCompetitionDto(commandDto.getCompetitionId());
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
