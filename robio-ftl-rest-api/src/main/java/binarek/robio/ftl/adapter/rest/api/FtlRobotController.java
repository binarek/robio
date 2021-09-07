package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.RobotAppService;
import binarek.robio.ftl.adapter.rest.api.dto.RegisterRobotCommandDto;
import binarek.robio.ftl.adapter.rest.api.dto.RobotDto;
import binarek.robio.ftl.adapter.rest.api.dto.RobotPatchDto;
import binarek.robio.ftl.command.SearchRobotCommand;
import binarek.robio.shared.model.RobotId;
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
    private final FtlRobotDtoMapper dtoMapper;

    FtlRobotController(RobotAppService robotAppService,
                       FtlRobotDtoMapper dtoMapper) {
        this.robotAppService = robotAppService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping
    @Valid RobotDto registerRobot(@RequestBody @Valid RegisterRobotCommandDto commandDto) {
        final var command = dtoMapper.toRegisterRobotCommand(commandDto);
        robotAppService.registerRobot(command);
        return getRobotDto(command.getRobotId());
    }

    @PatchMapping("/{robotId}")
    @Valid RobotDto changeRobotQualification(@PathVariable UUID robotId,
                                             @RequestBody @Valid RobotPatchDto dto) {
        final var command = dtoMapper.toChangeRobotQualificationCommand(robotId);

        if (dto.getQualification() == QUALIFIED) {
            robotAppService.qualifyRobot(command);
        } else if (dto.getQualification() == DISQUALIFIED) {
            robotAppService.disqualifyRobot(command);
        } else {
            throw new IllegalStateException("Robot qualification " + dto.getQualification() + " is not supported!");
        }
        return getRobotDto(command.getRobotId());
    }

    private RobotDto getRobotDto(RobotId robotId) {
        return dtoMapper.toRobotDto(robotAppService.getRobot(SearchRobotCommand.of(robotId)));
    }
}
