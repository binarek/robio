package binarek.robio.ftl.adapter.persistence;

import binarek.robio.ftl.CompetitionPlanRepository;
import binarek.robio.ftl.adapter.persistence.configuration.FtlBeanNames;
import binarek.robio.ftl.adapter.persistence.db.tables.records.CompetitionPlanRecord;
import binarek.robio.ftl.model.CompetitionPlan;
import binarek.robio.shared.exception.EntityHasChangedException;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.exception.DataChangedException;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static binarek.robio.ftl.adapter.persistence.db.tables.CompetitionPlan.COMPETITION_PLAN;
import static binarek.robio.ftl.adapter.persistence.db.tables.CompetitionPlanRobot.COMPETITION_PLAN_ROBOT;
import static binarek.robio.ftl.adapter.persistence.db.tables.Robot.ROBOT;

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
                .map(planRecord -> mapper.toCompetitionPlan(planRecord, fetchRobotIdsByCompetitionPlanId(planRecord.getId())));
    }

    private void insertPlan(CompetitionPlan plan) {
        var planRecord = buildNewRecord(plan);
        mapper.update(planRecord, plan);
        planRecord.store();

        insertRobots(plan.getRobots(), planRecord.getId());
    }

    private void insertRobots(Set<RobotId> robots, Long planRecordId) {
        final var robotIds = robots.stream()
                .map(RobotId::getValue)
                .collect(Collectors.toSet());

        dsl.insertInto(COMPETITION_PLAN_ROBOT)
                .select(dsl.select(DSL.val(planRecordId), ROBOT.ID)
                        .from(ROBOT)
                        .where(ROBOT.ROBOT_ID.in(robotIds)))
                .execute();
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

    private List<UUID> fetchRobotIdsByCompetitionPlanId(Long planId) {
        return dsl.select(ROBOT.ROBOT_ID)
                .from(ROBOT)
                .whereExists(dsl.selectOne()
                        .from(COMPETITION_PLAN_ROBOT)
                        .where(COMPETITION_PLAN_ROBOT.COMPETITION_PLAN_ID.eq(planId))
                        .and(COMPETITION_PLAN_ROBOT.ROBOT_ID.eq(ROBOT.ID)))
                .fetch()
                .map(Record1::value1);
    }

    private CompetitionPlanRecord buildNewRecord(CompetitionPlan plan) {
        var record = dsl.newRecord(COMPETITION_PLAN);
        mapper.update(record, plan);
        return record;
    }
}
