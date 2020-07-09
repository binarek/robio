package binarek.robio.persistence.robot;

import binarek.robio.db.tables.records.RobotRecord;
import binarek.robio.domain.robot.*;
import binarek.robio.domain.team.TeamId;
import binarek.robio.persistence.common.EntityTableHelper;
import org.jooq.DSLContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static binarek.robio.db.tables.Robot.ROBOT;
import static binarek.robio.persistence.common.EntityRepositoryUtil.*;

@Repository
public class RobotRepositoryImpl implements RobotRepository {

    private final DSLContext dsl;
    private final RobotTableMapper robotTableMapper;
    private final EntityTableHelper<RobotRecord> robotTableHelper;

    public RobotRepositoryImpl(DSLContext dsl, RobotTableMapper robotTableMapper) {
        this.dsl = dsl;
        this.robotTableMapper = robotTableMapper;
        this.robotTableHelper = new EntityTableHelper<>(Robot.class, dsl, ROBOT);
    }

    @Override
    public Optional<Robot> getById(RobotId id, @Nullable RobotFetchProperties fetchProperties) {
        return robotTableHelper.getByExternalId(id.getValue())
                .map(robotTableMapper::toRobot);
    }

    @Override
    public List<? extends Robot> getAll(@Nullable RobotFetchProperties fetchProperties) {
        return getRobotRecords(fetchProperties).stream()
                .map(robotTableMapper::toRobot)
                .collect(Collectors.toUnmodifiableList());
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
        var robotRecord = robotTableHelper.insert(record -> robotTableMapper.updateRecord(record, robot));
        return robotTableMapper.toRobot(robotRecord);
    }

    @Override
    public Robot insertOrUpdate(Robot robot) {
        var robotRecord = robotTableHelper.insertOrUpdate(
                getValueNullSafe(robot.getId()), record -> robotTableMapper.updateRecord(record, robot));
        return robotTableMapper.toRobot(robotRecord);
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

    private List<RobotRecord> getRobotRecords(@Nullable RobotFetchProperties fetchProperties) {
        return robotTableHelper.getAll(
                getLimit(fetchProperties),
                getOffset(fetchProperties),
                getSort(fetchProperties, robotTableMapper::toField));
    }

    @Nullable
    private static UUID getValueNullSafe(@Nullable RobotId robotId) {
        return robotId != null ? robotId.getValue() : null;
    }
}
