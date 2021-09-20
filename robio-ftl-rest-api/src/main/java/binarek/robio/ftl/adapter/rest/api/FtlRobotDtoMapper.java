package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.adapter.rest.api.dto.RegisterRobotCommandDto;
import binarek.robio.ftl.adapter.rest.api.dto.RobotDto;
import binarek.robio.ftl.command.ChangeRobotQualificationCommand;
import binarek.robio.ftl.command.RegisterRobotCommand;
import binarek.robio.ftl.command.SearchRobotCommand;
import binarek.robio.ftl.command.SearchRobotsCommand;
import binarek.robio.ftl.view.RobotView;
import binarek.robio.shared.SharedMapper;
import binarek.robio.util.codegen.BaseMapperConfig;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Mapper(config = BaseMapperConfig.class, uses = SharedMapper.class)
interface FtlRobotDtoMapper {

    RegisterRobotCommand toRegisterRobotCommand(UUID competitionId, RegisterRobotCommandDto dto);

    ChangeRobotQualificationCommand toChangeRobotQualificationCommand(UUID competitionId, UUID robotId);

    SearchRobotCommand toSearchRobotCommand(UUID competitionId, UUID robotId);

    SearchRobotsCommand toSearchRobotsCommand(UUID competitionId);

    RobotDto toRobotDto(RobotView robot);

    List<RobotDto> toRobotDtos(Collection<? extends RobotView> robot);
}
