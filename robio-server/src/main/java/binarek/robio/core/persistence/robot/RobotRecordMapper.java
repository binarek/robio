package binarek.robio.core.persistence.robot;

import binarek.robio.common.domain.value.StandardValueMapper;
import binarek.robio.core.domain.robot.Robot;
import binarek.robio.core.domain.robot.RobotName;
import binarek.robio.db.tables.records.RobotRecord;
import binarek.robio.codegen.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = BaseMapperConfig.class, uses = StandardValueMapper.class)
public interface RobotRecordMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    void updateRecord(@MappingTarget RobotRecord robotRecord, Robot robot);

    @Mapping(target = "id", source = "externalId")
    Robot toRobot(RobotRecord robotRecord);

    default RobotName toRobotName(String name) {
        return RobotName.of(name);
    }

    default String toValue(RobotName name) {
        return name.getValue();
    }
}
