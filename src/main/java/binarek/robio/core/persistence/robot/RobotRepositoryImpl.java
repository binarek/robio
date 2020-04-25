package binarek.robio.core.persistence.robot;

import binarek.robio.common.persistence.DomainEntityRepositoryImpl;
import binarek.robio.core.domain.robot.Robot;
import binarek.robio.core.domain.robot.RobotRepository;
import binarek.robio.db.tables.records.RobotRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static binarek.robio.db.tables.Robot.ROBOT;

@Repository
public class RobotRepositoryImpl extends DomainEntityRepositoryImpl<Robot, RobotRecord> implements RobotRepository {

    public RobotRepositoryImpl(DSLContext dsl, RobotRecordMapper robotRecordMapper) {
        super(dsl, ROBOT, robotRecordMapper);
    }
}
