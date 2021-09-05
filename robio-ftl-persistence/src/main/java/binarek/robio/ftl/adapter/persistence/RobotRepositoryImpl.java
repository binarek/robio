package binarek.robio.ftl.adapter.persistence;

import binarek.robio.ftl.RobotRepository;
import binarek.robio.ftl.adapter.persistence.configuration.FtlBeanNames;
import binarek.robio.ftl.model.Robot;
import binarek.robio.shared.model.RobotId;
import org.jooq.DSLContext;
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
        var record = dsl.newRecord(ROBOT);
        mapper.update(record, robot);
        record.store();
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
}
