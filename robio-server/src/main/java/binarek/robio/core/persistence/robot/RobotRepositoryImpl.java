package binarek.robio.core.persistence.robot;

import binarek.robio.common.persistence.EntityFetchProperties;
import binarek.robio.common.persistence.EntityTableHelper;
import binarek.robio.core.domain.robot.Robot;
import binarek.robio.core.domain.robot.RobotId;
import binarek.robio.core.domain.robot.RobotName;
import binarek.robio.core.domain.robot.RobotRepository;
import binarek.robio.core.domain.team.TeamId;
import binarek.robio.db.tables.records.RobotRecord;
import org.jooq.DSLContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static binarek.robio.db.tables.Robot.ROBOT;

@Repository
public class RobotRepositoryImpl implements RobotRepository {

    private final DSLContext dsl;
    private final RobotRecordMapper robotRecordMapper;
    private final EntityTableHelper<RobotRecord> robotTableHelper;

    public RobotRepositoryImpl(DSLContext dsl, RobotRecordMapper robotRecordMapper) {
        this.dsl = dsl;
        this.robotRecordMapper = robotRecordMapper;
        this.robotTableHelper = new EntityTableHelper<>(Robot.class, dsl, ROBOT);
    }

    @Override
    public Optional<Robot> getById(RobotId id, @Nullable EntityFetchProperties.NotSupported fetchProperties) {
        return robotTableHelper.getByExternalId(id.getValue())
                .map(robotRecordMapper::toRobot);
    }

    @Override
    public boolean existsByName(RobotName name) {
        return robotTableHelper.existsByName(name.getValue());
    }

    @Override
    public boolean existsByIdOrName(@Nullable RobotId id, RobotName name) {
        return robotTableHelper.existsByExternalIdOrName(getValueNullSafe(id), name.getValue());
    }

    @Override
    public Robot insert(Robot robot) {
        var robotRecord = robotTableHelper.insert(record -> robotRecordMapper.updateRecord(record, robot));
        return robotRecordMapper.toRobot(robotRecord);
    }

    @Override
    public Robot insertOrUpdate(Robot robot) {
        var robotRecord = robotTableHelper.insertOrUpdate(
                getValueNullSafe(robot.getId()), record -> robotRecordMapper.updateRecord(record, robot));
        return robotRecordMapper.toRobot(robotRecord);
    }

    @Override
    public boolean deleteById(RobotId id) {
        return robotTableHelper.deleteByExternalId(id.getValue());
    }

    @Override
    public boolean existsByTeamId(TeamId teamId) {
        return dsl.selectOne().from(ROBOT).where(ROBOT.TEAM_ID.eq(teamId.getValue())).fetchOne() != null;
    }

    @Override
    public List<RobotId> getIdsByTeamId(TeamId teamId) {
        return dsl.select().from(ROBOT).where(ROBOT.TEAM_ID.eq(teamId.getValue())).fetch(ROBOT.EXTERNAL_ID).stream()
                .map(RobotId::of)
                .collect(Collectors.toUnmodifiableList());
    }

    @Nullable
    private static UUID getValueNullSafe(@Nullable RobotId robotId) {
        return robotId != null ? robotId.getValue() : null;
    }
}
