package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.RobotAppService;
import binarek.robio.ftl.adapter.rest.api.dto.RegisterRobotCommandDto;
import binarek.robio.ftl.adapter.rest.api.dto.RobotDto;
import binarek.robio.ftl.command.SearchRobotCommand;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
        var command = dtoMapper.toRegisterRobotCommand(commandDto);
        robotAppService.registerRobot(command);
        return dtoMapper.toRobotDto(robotAppService.getRobot(SearchRobotCommand.of(command.getRobotId())));
    }
}
