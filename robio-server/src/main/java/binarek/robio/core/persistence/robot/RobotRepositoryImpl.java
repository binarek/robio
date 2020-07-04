package binarek.robio.core.persistence.robot;

import binarek.robio.common.domain.entity.EntityFetchProperties;
import binarek.robio.common.persistence.EntityTableHelper;
import binarek.robio.core.domain.robot.Robot;
import binarek.robio.core.domain.robot.RobotFetchProperties;
import binarek.robio.core.domain.robot.RobotRepository;
import binarek.robio.core.domain.robot.RobotSortableField;
import binarek.robio.db.tables.records.RobotRecord;
import org.jooq.DSLContext;
import org.jooq.TableField;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static binarek.robio.common.util.MapperUtil.mapNullSafe;
import static binarek.robio.db.tables.Robot.ROBOT;

@Repository
public class RobotRepositoryImpl implements RobotRepository {

    private final DSLContext dsl;
    private final RobotRecordMapper robotRecordMapper;
    private final RobotTableFieldMapper robotTableFieldMapper;
    private final EntityTableHelper<RobotRecord> robotTableHelper;

    public RobotRepositoryImpl(DSLContext dsl, RobotRecordMapper robotRecordMapper,
                               RobotTableFieldMapper robotTableFieldMapper) {
        this.dsl = dsl;
        this.robotRecordMapper = robotRecordMapper;
        this.robotTableFieldMapper = robotTableFieldMapper;
        this.robotTableHelper = new EntityTableHelper<>(Robot.class, dsl, ROBOT);
    }

    @Override
    public Optional<Robot> getById(UUID id, @Nullable RobotFetchProperties fetchProperties) {
        return robotTableHelper.getByExternalId(id)
                .map(robotRecordMapper::toRobot);
    }

    @Override
    public List<? extends Robot> getAll(@Nullable RobotFetchProperties fetchProperties) {
        return getRobotRecords(fetchProperties).stream()
                .map(robotRecordMapper::toRobot)
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
        var robotRecord = robotTableHelper.insert(record -> robotRecordMapper.updateRecord(record, robot));
        return robotRecordMapper.toRobot(robotRecord);
    }

    @Override
    public Robot insertOrUpdate(Robot robot) {
        var robotRecord = robotTableHelper.insertOrUpdate(
                robot.getIdValue(), record -> robotRecordMapper.updateRecord(record, robot));
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

    @Override
    public List<UUID> getIdsByTeamId(UUID teamId) {
        return dsl.select().from(ROBOT).where(ROBOT.TEAM_ID.eq(teamId)).fetch(ROBOT.EXTERNAL_ID);
    }

    @SuppressWarnings("unchecked")
    private List<RobotRecord> getRobotRecords(@Nullable EntityFetchProperties<RobotSortableField> fetchProperties) {
        Integer limit = null;
        Integer offset = null;
        List<TableField<RobotRecord, ?>> orderFields = List.of();

        if (fetchProperties != null) {
            limit = fetchProperties.getLimit();
            offset = fetchProperties.getOffset();
            orderFields = (List<TableField<RobotRecord, ?>>) mapNullSafe(fetchProperties.getSort(), robotTableFieldMapper::toField);
        }
        return robotTableHelper.getAll(limit, offset, orderFields);
    }
}
