package binarek.robio.ftl.adapter.persistence;

import binarek.robio.ftl.adapter.persistence.configuration.FtlBeanNames;
import binarek.robio.ftl.adapter.persistence.db.tables.records.CompetitionPlanRecord;
import binarek.robio.ftl.adapter.persistence.db.tables.records.CompetitionPlanRobotRecord;
import binarek.robio.ftl.planning.CompetitionPlanRepository;
import binarek.robio.ftl.planning.model.CompetitionPlan;
import binarek.robio.ftl.planning.model.RobotPlaceholder;
import binarek.robio.shared.exception.EntityHasChangedException;
import binarek.robio.shared.model.CompetitionId;
import org.jooq.DSLContext;
import org.jooq.exception.DataChangedException;
import org.jooq.impl.UpdatableRecordImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static binarek.robio.ftl.adapter.persistence.db.tables.CompetitionPlan.COMPETITION_PLAN;
import static binarek.robio.ftl.adapter.persistence.db.tables.CompetitionPlanRobot.COMPETITION_PLAN_ROBOT;

@Repository
class CompetitionPlanRepositoryImpl implements CompetitionPlanRepository {

    private final DSLContext dsl;
    private final CompetitionPlanRecordsMapper mapper;

    CompetitionPlanRepositoryImpl(@Qualifier(FtlBeanNames.DSL_CONTEXT) DSLContext dsl,
                                  CompetitionPlanRecordsMapper mapper) {
        this.dsl = dsl;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void save(CompetitionPlan plan) {
        dsl.fetchOptional(COMPETITION_PLAN, COMPETITION_PLAN.COMPETITION_ID.eq(plan.getCompetitionId().getValue()))
                .ifPresentOrElse(
                        planRecord -> updatePlan(planRecord, plan),
                        () -> insertPlan(plan));
    }

    @Override
    public boolean existsByCompetitionId(CompetitionId competitionId) {
        return dsl.fetchExists(dsl.selectFrom(COMPETITION_PLAN)
                .where(COMPETITION_PLAN.COMPETITION_ID.eq(competitionId.getValue())));
    }

    @Override
    public Optional<CompetitionPlan> getByCompetitionId(CompetitionId competitionId) {
        return dsl.selectFrom(COMPETITION_PLAN)
                .where(COMPETITION_PLAN.COMPETITION_ID.eq(competitionId.getValue()))
                .fetchOptional()
                .map(planRecord -> {
                    var robotRecords = dsl.selectFrom(COMPETITION_PLAN_ROBOT)
                            .where(COMPETITION_PLAN_ROBOT.COMPETITION_PLAN_ID.eq(planRecord.getId()))
                            .fetch();
                    return mapper.toCompetitionPlan(planRecord, robotRecords);
                });
    }

    private void insertPlan(CompetitionPlan plan) {
        var planRecord = buildNewRecord(plan);
        mapper.update(planRecord, plan);
        planRecord.store();

        insertRobots(plan.getRobots(), planRecord.getId());
    }

    private void insertRobots(List<RobotPlaceholder> robots, Long planRobotId) {
        robots.stream()
                .map(robot -> buildNewRecord(robot, planRobotId))
                .forEach(UpdatableRecordImpl::store);
    }

    private void updatePlan(CompetitionPlanRecord planRecord, CompetitionPlan plan) {
        mapper.update(planRecord, plan);
        try {
            planRecord.store();
        } catch (DataChangedException e) {
            throw new EntityHasChangedException(e);
        }

        deleteRobots(planRecord.getId());
        insertRobots(plan.getRobots(), planRecord.getId());
    }

    private void deleteRobots(Long planRecordId) {
        dsl.deleteFrom(COMPETITION_PLAN_ROBOT)
                .where(COMPETITION_PLAN_ROBOT.COMPETITION_PLAN_ID.eq(planRecordId))
                .execute();
    }

    private CompetitionPlanRecord buildNewRecord(CompetitionPlan plan) {
        var record = dsl.newRecord(COMPETITION_PLAN);
        mapper.update(record, plan);
        return record;
    }

    private CompetitionPlanRobotRecord buildNewRecord(RobotPlaceholder robot, Long competitionPlanId) {
        var record = dsl.newRecord(COMPETITION_PLAN_ROBOT);
        mapper.update(record, robot, competitionPlanId);
        return record;
    }
}
