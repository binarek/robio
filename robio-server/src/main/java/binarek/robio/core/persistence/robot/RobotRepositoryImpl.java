package binarek.robio.core.persistence.robot;

import binarek.robio.common.domain.DomainEntityDetailsLevel;
import binarek.robio.common.persistence.DomainEntityTableHelper;
import binarek.robio.core.domain.robot.Robot;
import binarek.robio.core.domain.robot.RobotChangedException;
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

    private final DSLContext dsl;
    private final RobotRecordMapper robotRecordMapper;
    private final DomainEntityTableHelper<RobotRecord> robotTableHelper;

    public RobotRepositoryImpl(DSLContext dsl, RobotRecordMapper robotRecordMapper) {
        this.dsl = dsl;
        this.robotRecordMapper = robotRecordMapper;
        this.robotTableHelper = new DomainEntityTableHelper<>(dsl, ROBOT, RobotChangedException::new);
    }

    @Override
    public Optional<Robot> getById(UUID id, @Nullable DomainEntityDetailsLevel detailsLevel) {
        return robotTableHelper.getByExternalId(id)
                .map(robotRecordMapper::toRobot);
    }

    @Override
    public boolean existsByName(String name) {
        return robotTableHelper.existsByName(name);
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
    public boolean deleteById(UUID id) {
        return robotTableHelper.deleteByExternalId(id);
    }

    @Override
    public boolean existsByTeamId(UUID teamId) {
        return dsl.selectOne().from(ROBOT).where(ROBOT.TEAM_ID.eq(teamId)).fetchOne() != null;
    }
}
