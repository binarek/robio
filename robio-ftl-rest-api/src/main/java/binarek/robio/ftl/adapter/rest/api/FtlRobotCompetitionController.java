package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.RobotAppService;
import binarek.robio.ftl.adapter.rest.api.dto.RegisterRobotCommandDto;
import binarek.robio.ftl.adapter.rest.api.dto.RobotDto;
import binarek.robio.ftl.adapter.rest.api.dto.RobotPatchDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static binarek.robio.ftl.adapter.rest.api.dto.RobotPatchDto.Qualification.DISQUALIFIED;
import static binarek.robio.ftl.adapter.rest.api.dto.RobotPatchDto.Qualification.QUALIFIED;

@RestController
@RequestMapping("/ftl/competitions/{competitionId}/robots")
@Validated
@Tag(name = "competitions")
@Tag(name = "robots")
class FtlRobotCompetitionController {

    private final RobotAppService robotAppService;
    private final FtlRobotDtoMapper robotDtoMapper;

    FtlRobotCompetitionController(RobotAppService robotAppService,
                                  FtlRobotDtoMapper robotDtoMapper) {
        this.robotAppService = robotAppService;
        this.robotDtoMapper = robotDtoMapper;
    }

    @PostMapping
    @Valid
    RobotDto registerRobot(@PathVariable UUID competitionId, @RequestBody @Valid RegisterRobotCommandDto commandDto) {
        final var command = robotDtoMapper.toRegisterRobotCommand(competitionId, commandDto);
        robotAppService.registerRobot(command);
        return getRobotDto(competitionId, commandDto.getRobotId());
    }

    @GetMapping("/{robotId}")
    @Valid
    RobotDto getRobot(@PathVariable UUID competitionId, @PathVariable UUID robotId) {
        return getRobotDto(competitionId, robotId);
    }

    @GetMapping
    @Valid
    List<@Valid RobotDto> getRobots(@PathVariable UUID competitionId) {
        final var query = robotDtoMapper.toRobotsByCompetitionIdQuery(competitionId);
        return robotDtoMapper.toRobotDtos(robotAppService.getRobots(query));
    }

    @PatchMapping("/{robotId}")
    @Valid
    RobotDto changeRobotQualification(@PathVariable UUID competitionId, @PathVariable UUID robotId,
                                      @RequestBody @Valid RobotPatchDto dto) {
        final var command = robotDtoMapper.toChangeRobotQualificationCommand(competitionId, robotId);

        if (dto.getQualification() == QUALIFIED) {
            robotAppService.qualifyRobot(command);
        } else if (dto.getQualification() == DISQUALIFIED) {
            robotAppService.disqualifyRobot(command);
        } else {
            throw new IllegalStateException("Robot qualification " + dto.getQualification() + " is not supported!");
        }
        return getRobotDto(competitionId, robotId);
    }

    private RobotDto getRobotDto(UUID competitionId, UUID robotId) {
        final var query = robotDtoMapper.toRobotByIdQuery(competitionId, robotId);
        return robotDtoMapper.toRobotDto(robotAppService.getRobot(query));
    }
}
