package binarek.robio.ftl.adapter.persistence;

import binarek.robio.ftl.adapter.persistence.db.tables.records.RobotRecord;
import binarek.robio.ftl.model.ImmutableRobot;
import binarek.robio.ftl.model.Robot;
import binarek.robio.shared.SharedMapper;
import binarek.robio.util.codegen.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = BaseMapperConfig.class, uses = SharedMapper.class)
interface RobotRecordsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifyProcess", ignore = true)
    void update(@MappingTarget RobotRecord record, Robot robot);

    ImmutableRobot toRobot(RobotRecord record);
}
