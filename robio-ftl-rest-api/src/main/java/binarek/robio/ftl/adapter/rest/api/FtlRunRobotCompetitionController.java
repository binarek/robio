package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.RunAppService;
import binarek.robio.ftl.adapter.rest.api.dto.AddRunCommandDto;
import binarek.robio.ftl.adapter.rest.api.dto.RunDto;
import binarek.robio.ftl.adapter.rest.api.dto.RunPatchDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/ftl/competitions/{competitionId}/robots/{robotId}/runs")
@Validated
@Tag(name = "runs")
class FtlRunRobotCompetitionController {

    private final RunAppService runAppService;
    private final FtlRunDtoMapper runDtoMapper;

    FtlRunRobotCompetitionController(RunAppService runAppService,
                                     FtlRunDtoMapper runDtoMapper) {
        this.runAppService = runAppService;
        this.runDtoMapper = runDtoMapper;
    }

    @PostMapping
    @Valid
    RunDto addRun(@PathVariable UUID competitionId, @PathVariable UUID robotId,
                  @RequestBody @Valid AddRunCommandDto dto) {
        final var command = runDtoMapper.toAddRunCommandDto(competitionId, robotId, dto);
        final var runNumber = runAppService.addRun(command);
        return getRunDto(competitionId, robotId, runNumber);
    }

    @PatchMapping("/{number}")
    @Valid
    RunDto editRun(@PathVariable UUID competitionId, @PathVariable UUID robotId, @PathVariable Integer number,
                   @RequestBody @Valid RunPatchDto dto) {
        final var command = runDtoMapper.toEditRunResultCommand(competitionId, robotId, number, dto);
        runAppService.editRunResult(command);
        return getRunDto(competitionId, robotId, number);
    }

    private RunDto getRunDto(UUID competitionId, UUID robotId, Integer number) {
        return runDtoMapper.toRunDto(runAppService.getRun(runDtoMapper.toRunQuery(competitionId, robotId, number)));
    }
}
