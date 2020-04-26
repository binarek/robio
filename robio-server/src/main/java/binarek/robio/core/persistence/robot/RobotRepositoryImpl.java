package binarek.robio.core.persistence.robot;

import binarek.robio.common.persistence.DomainEntityTableHelper;
import binarek.robio.core.domain.robot.Robot;
import binarek.robio.core.domain.robot.RobotRepository;
import binarek.robio.db.tables.records.RobotRecord;
import org.jooq.DSLContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static binarek.robio.db.tables.Robot.ROBOT;

@Repository
public class RobotRepositoryImpl implements RobotRepository {

    private final RobotRecordMapper robotRecordMapper;
    private final DomainEntityTableHelper<RobotRecord> robotTableHelper;

    public RobotRepositoryImpl(DSLContext dsl, RobotRecordMapper robotRecordMapper) {
        this.robotRecordMapper = robotRecordMapper;
        this.robotTableHelper = new DomainEntityTableHelper<>(dsl, ROBOT);
    }

    @Override
    public Optional<Robot> getById(UUID id) {
        return robotTableHelper.getByExternalId(id)
                .map(robotRecordMapper::toRobot);
    }

    @Override
    public boolean existsByIdOrName(@Nullable UUID id, String name) {
        return robotTableHelper.existsByExternalIdOrName(id, name);
    }

    @Override
    public Robot insert(Robot robot) {
        var robotRecord = robotTableHelper.insert(record -> robotRecordMapper.updateRecord(record, robot));
        return robotRecordMapper.toRobot(robotRecord);
    }

    @Override
    public Robot insertOrUpdate(Robot robot) {
        var robotRecord = robotTableHelper.insertOrUpdate(
                robot.getId(), record -> robotRecordMapper.updateRecord(record, robot));
        return robotRecordMapper.toRobot(robotRecord);
    }

    @Override
    public boolean delete(UUID id) {
        return robotTableHelper.deleteByExternalId(id);
    }
}
