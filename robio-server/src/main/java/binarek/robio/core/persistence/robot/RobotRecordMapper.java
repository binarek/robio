package binarek.robio.core.persistence.robot;

import binarek.robio.codegen.BaseMapperConfig;
import binarek.robio.common.domain.value.CommonValueMapper;
import binarek.robio.core.domain.robot.Robot;
import binarek.robio.core.domain.robot.RobotValueMapper;
import binarek.robio.core.domain.team.TeamValueMapper;
import binarek.robio.db.tables.records.RobotRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = BaseMapperConfig.class, uses = {CommonValueMapper.class, RobotValueMapper.class, TeamValueMapper.class})
public interface RobotRecordMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    void updateRecord(@MappingTarget RobotRecord robotRecord, Robot robot);

    @Mapping(target = "id", source = "externalId")
    Robot toRobot(RobotRecord robotRecord);
}
