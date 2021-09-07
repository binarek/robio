package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.adapter.rest.api.dto.RegisterRobotCommandDto;
import binarek.robio.ftl.adapter.rest.api.dto.RobotDto;
import binarek.robio.ftl.command.ChangeRobotQualificationCommand;
import binarek.robio.ftl.command.RegisterRobotCommand;
import binarek.robio.ftl.view.RobotView;
import binarek.robio.shared.SharedMapper;
import binarek.robio.util.codegen.BaseMapperConfig;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(config = BaseMapperConfig.class, uses = SharedMapper.class)
interface FtlRobotDtoMapper {

    RegisterRobotCommand toRegisterRobotCommand(RegisterRobotCommandDto dto);

    ChangeRobotQualificationCommand toChangeRobotQualificationCommand(UUID robotId);

    RobotDto toRobotDto(RobotView robot);
}