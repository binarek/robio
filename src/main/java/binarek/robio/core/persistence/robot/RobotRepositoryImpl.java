package binarek.robio.core.persistence.robot;

import binarek.robio.core.domain.robot.Robot;
import binarek.robio.core.domain.robot.RobotRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static binarek.robio.db.tables.Robot.ROBOT;

@Repository
public class RobotRepositoryImpl implements RobotRepository {

    private final DSLContext dsl;
    private final RobotRecordMapper robotRecordMapper;

    public RobotRepositoryImpl(DSLContext dsl, RobotRecordMapper robotRecordMapper) {
        this.dsl = dsl;
        this.robotRecordMapper = robotRecordMapper;
    }

    @Override
    public Optional<Robot> getById(UUID id) {
        return Optional.ofNullable(dsl.fetchOne(ROBOT, ROBOT.EXTERNAL_ID.eq(id)))
                .map(robotRecordMapper::toRobot);
    }

    @Override
    public Robot insert(Robot robot) {
        var record = dsl.newRecord(ROBOT);
        robotRecordMapper.updateRobotRecord(record, robot);
        if (record.getExternalId() == null) {
            record.setExternalId(UUID.randomUUID());
        }
        record.store();
        return robotRecordMapper.toRobot(record);
    }
}
