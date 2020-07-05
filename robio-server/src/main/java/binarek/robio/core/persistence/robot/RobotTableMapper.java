package binarek.robio.core.persistence.robot;

import binarek.robio.codegen.BaseMapperConfig;
import binarek.robio.common.domain.value.StandardValueMapper;
import binarek.robio.core.domain.robot.Robot;
import binarek.robio.core.domain.robot.RobotSortableField;
import binarek.robio.core.domain.robot.RobotValueMapper;
import binarek.robio.core.domain.team.TeamValueMapper;
import binarek.robio.db.tables.records.RobotRecord;
import org.jooq.TableField;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static binarek.robio.db.tables.Robot.ROBOT;

@Mapper(config = BaseMapperConfig.class,
        uses = {StandardValueMapper.class, RobotValueMapper.class, TeamValueMapper.class})
public interface RobotTableMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    void updateRecord(@MappingTarget RobotRecord robotRecord, Robot robot);

    @Mapping(target = "id", source = "externalId")
    Robot toRobot(RobotRecord robotRecord);

    default TableField<RobotRecord, ?> toField(RobotSortableField robotSortableField) {
        switch (robotSortableField) {
            case HEIGHT:
                return ROBOT.HEIGHT;
            case LENGTH:
                return ROBOT.LENGTH;
            case NAME:
                return ROBOT.NAME;
            case WEIGHT:
                return ROBOT.WEIGHT;
            case WIDTH:
                return ROBOT.WIDTH;
            default:
                throw new IllegalArgumentException("Field of type " + robotSortableField + " is not supported");
        }
    }
}
