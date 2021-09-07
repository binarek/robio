package binarek.robio.ftl.adapter.persistence;

import binarek.robio.ftl.RobotRepository;
import binarek.robio.ftl.adapter.persistence.configuration.FtlBeanNames;
import binarek.robio.ftl.adapter.persistence.db.tables.records.RobotRecord;
import binarek.robio.ftl.model.Robot;
import binarek.robio.shared.exception.EntityHasChangedException;
import binarek.robio.shared.model.RobotId;
import org.jooq.DSLContext;
import org.jooq.exception.DataChangedException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static binarek.robio.ftl.adapter.persistence.db.tables.Robot.ROBOT;

@Repository
public class RobotRepositoryImpl implements RobotRepository {

    private final DSLContext dsl;
    private final RobotRecordsMapper mapper;

    public RobotRepositoryImpl(@Qualifier(FtlBeanNames.DSL_CONTEXT) DSLContext dsl,
                               RobotRecordsMapper mapper) {
        this.dsl = dsl;
        this.mapper = mapper;
    }

    @Override
    public void save(Robot robot) {
        dsl.fetchOptional(ROBOT, ROBOT.ROBOT_ID.eq(robot.getRobotId().getValue()))
                .ifPresentOrElse(
                        robotRecord -> updateRobot(robotRecord, robot),
                        () -> insertRobot(robot));
    }

    @Override
    public boolean existsByRobotId(RobotId robotId) {
        return dsl.fetchExists(dsl.selectFrom(ROBOT)
                .where(ROBOT.ROBOT_ID.eq(robotId.getValue())));
    }

    @Override
    public Optional<Robot> getByRobotId(RobotId robotId) {
        return dsl.selectFrom(ROBOT)
                .where(ROBOT.ROBOT_ID.eq(robotId.getValue()))
                .fetchOptional()
                .map(mapper::toRobot);
    }

    private void insertRobot(Robot robot) {
        final var record = dsl.newRecord(ROBOT);
        mapper.update(record, robot);
        record.store();
    }

    private void updateRobot(RobotRecord robotRecord, Robot robot) {
        mapper.update(robotRecord, robot);
        try {
            robotRecord.store();
        } catch (DataChangedException e) {
            throw new EntityHasChangedException(e);
        }
    }
}
