package binarek.robio.core.persistence.robot;

import binarek.robio.core.domain.robot.RobotSortableField;
import binarek.robio.db.tables.records.RobotRecord;
import org.jooq.TableField;
import org.springframework.stereotype.Component;

import static binarek.robio.db.tables.Robot.ROBOT;

@Component
public class RobotTableFieldMapper {

    public TableField<RobotRecord, ?> toField(RobotSortableField robotSortableField) {
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
