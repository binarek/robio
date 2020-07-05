package binarek.robio.core.persistence.robot;

import binarek.robio.common.persistence.EntityTableHelper;
import binarek.robio.core.domain.robot.Robot;
import binarek.robio.core.domain.robot.RobotFetchProperties;
import binarek.robio.core.domain.robot.RobotId;
import binarek.robio.core.domain.robot.RobotRepository;
import binarek.robio.core.domain.team.TeamId;
import binarek.robio.db.tables.records.RobotRecord;
import org.jooq.DSLContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static binarek.robio.common.persistence.EntityPersistenceUtil.*;
import static binarek.robio.db.tables.Robot.ROBOT;

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
    public Optional<Robot> getById(UUID id, @Nullable RobotFetchProperties fetchProperties) {
        return robotTableHelper.getByExternalId(id)
                .map(robotTableMapper::toRobot);
    }

    @Override
    public List<? extends Robot> getAll(@Nullable RobotFetchProperties fetchProperties) {
        return getRobotRecords(fetchProperties).stream()
                .map(robotTableMapper::toRobot)
                .collect(Collectors.toUnmodifiableList());
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
        var robotRecord = robotTableHelper.insert(record -> robotTableMapper.updateRecord(record, robot));
        return robotTableMapper.toRobot(robotRecord);
    }

    @Override
    public Robot insertOrUpdate(Robot robot) {
        var robotRecord = robotTableHelper.insertOrUpdate(
                robot.getIdValue(), record -> robotTableMapper.updateRecord(record, robot));
        return robotTableMapper.toRobot(robotRecord);
    }

    @Override
    public boolean deleteById(UUID id) {
        return robotTableHelper.deleteByExternalId(id);
    }

    @Override
    public boolean existsByTeamId(UUID teamId) {
        return dsl.selectOne().from(ROBOT).where(ROBOT.TEAM_ID.eq(teamId)).fetchOne() != null;
    }

    @Override
    public List<UUID> getIdsByTeamId(UUID teamId) {
        return dsl.select().from(ROBOT).where(ROBOT.TEAM_ID.eq(teamId)).fetch(ROBOT.EXTERNAL_ID);
    }

    @Override
    public Map<TeamId, List<RobotId>> getIdsByTeamIds(List<TeamId> ids) {
        var teamIdValues = ids.stream().map(TeamId::getValue).collect(Collectors.toUnmodifiableList());
        return dsl.select(ROBOT.TEAM_ID, ROBOT.EXTERNAL_ID)
                .from(ROBOT)
                .where(ROBOT.TEAM_ID.in(teamIdValues))
                .fetch().stream()
                .collect(Collectors.groupingBy(
                        result -> TeamId.of(result.value1()),
                        Collectors.mapping(result -> RobotId.of(result.value2()), Collectors.toList())));
    }

    private List<RobotRecord> getRobotRecords(@Nullable RobotFetchProperties fetchProperties) {
        return robotTableHelper.getAll(
                getLimit(fetchProperties),
                getOffset(fetchProperties),
                getSort(fetchProperties, robotTableMapper::toField));
    }
}
