package binarek.robio.core.persistence.robot;

import binarek.robio.common.persistence.DomainEntityRecordMapper;
import binarek.robio.core.domain.robot.Robot;
import binarek.robio.db.tables.records.RobotRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RobotRecordMapper extends DomainEntityRecordMapper<Robot, RobotRecord> {

    @Mapping(target = "value1", ignore = true)
    @Mapping(target = "value2", ignore = true)
    @Mapping(target = "value3", ignore = true)
    @Mapping(target = "value4", ignore = true)
    @Mapping(target = "value5", ignore = true)
    @Mapping(target = "value6", ignore = true)
    @Mapping(target = "value7", ignore = true)
    @Mapping(target = "value8", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    void updateRecord(@MappingTarget RobotRecord robotRecord, Robot robot);

    @Mapping(target = "id", source = "externalId")
    Robot toEntity(RobotRecord robotRecord);
}
