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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    void updateRecord(@MappingTarget RobotRecord record, Robot robot);

    @Mapping(target = "id", source = "externalId")
    Robot toEntity(RobotRecord record);
}
