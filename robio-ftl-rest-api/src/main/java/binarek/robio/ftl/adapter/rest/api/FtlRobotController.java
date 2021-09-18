package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.RobotAppService;
import binarek.robio.ftl.adapter.rest.api.dto.RegisterRobotCommandDto;
import binarek.robio.ftl.adapter.rest.api.dto.RobotDto;
import binarek.robio.ftl.adapter.rest.api.dto.RobotPatchDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static binarek.robio.ftl.adapter.rest.api.dto.RobotPatchDto.Qualification.DISQUALIFIED;
import static binarek.robio.ftl.adapter.rest.api.dto.RobotPatchDto.Qualification.QUALIFIED;

@RestController
@RequestMapping("/ftl/robots")
@Validated
class FtlRobotController {

    private final RobotAppService robotAppService;
    private final FtlRobotDtoMapper robotDtoMapper;

    FtlRobotController(RobotAppService robotAppService,
                       FtlRobotDtoMapper robotDtoMapper) {
        this.robotAppService = robotAppService;
        this.robotDtoMapper = robotDtoMapper;
    }

    @PostMapping
    @Valid
    RobotDto registerRobot(@RequestBody @Valid RegisterRobotCommandDto commandDto) {
        final var command = robotDtoMapper.toRegisterRobotCommand(commandDto);
        robotAppService.registerRobot(command);
        return getRobotDto(commandDto.getCompetitionId(), commandDto.getRobotId());
    }

    @GetMapping("/{competitionId}/{robotId}")
    @Valid
    RobotDto getRobot(@PathVariable UUID competitionId, @PathVariable UUID robotId) {
        return getRobotDto(competitionId, robotId);
    }

    @PatchMapping("/{competitionId}/{robotId}")
    @Valid
    RobotDto changeRobotQualification(@PathVariable UUID competitionId,
                                      @PathVariable UUID robotId,
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
        final var command = robotDtoMapper.toSearchRobotCommand(competitionId, robotId);
        return robotDtoMapper.toRobotDto(robotAppService.getRobot(command));
    }
}
