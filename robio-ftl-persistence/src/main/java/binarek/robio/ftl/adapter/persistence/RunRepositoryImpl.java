package binarek.robio.ftl.adapter.persistence;

import binarek.robio.ftl.RunRepository;
import binarek.robio.ftl.adapter.persistence.configuration.FtlBeanNames;
import binarek.robio.ftl.adapter.persistence.db.tables.records.RunRecord;
import binarek.robio.ftl.model.Run;
import binarek.robio.shared.exception.EntityHasChangedException;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Select;
import org.jooq.exception.DataChangedException;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static binarek.robio.ftl.adapter.persistence.db.tables.Run.RUN;

@Repository
class RunRepositoryImpl implements RunRepository {

    private final DSLContext dsl;
    private final RunRecordsMapper runRecordsMapper;

    RunRepositoryImpl(@Qualifier(FtlBeanNames.DSL_CONTEXT) DSLContext dsl,
                      RunRecordsMapper runRecordsMapper) {
        this.dsl = dsl;
        this.runRecordsMapper = runRecordsMapper;
    }

    @Override
    public void save(Run run) {
        dsl.fetchOptional(RUN,
                RUN.COMPETITION_ID.eq(run.getCompetitionId().getValue()),
                RUN.ROBOT_ID.eq(run.getRobotId().getValue()),
                RUN.NUMBER.eq(run.getNumber()))
                .ifPresentOrElse(
                        record -> update(record, run),
                        () -> insert(run));
    }

    @Override
    public int countByCompetitionIdAndRobotId(CompetitionId competitionId, RobotId robotId) {
        return dsl.fetchCount(RUN, RUN.COMPETITION_ID.eq(competitionId.getValue()), RUN.ROBOT_ID.eq(robotId.getValue()));
    }

    @Override
    public Optional<Run> getByCompetitionIdAndRobotIdAndNumber(CompetitionId competitionId, RobotId robotId, Integer number) {
        return dsl.selectFrom(RUN)
                .where(RUN.COMPETITION_ID.eq(competitionId.getValue()))
                .and(RUN.ROBOT_ID.eq(robotId.getValue()))
                .and(RUN.NUMBER.eq(number))
                .fetchOptional(runRecordsMapper::toRun);
    }

    @Override
    public Optional<Run> getByCompetitionIdAndRobotIdAndMaxNumber(CompetitionId competitionId, RobotId robotId) {
        return dsl.selectFrom(RUN)
                .where(RUN.COMPETITION_ID.eq(competitionId.getValue()))
                .and(RUN.ROBOT_ID.eq(robotId.getValue()))
                .and(RUN.NUMBER.eq(maxNumberQuery(competitionId, robotId)))
                .fetchOptional(runRecordsMapper::toRun);
    }

    private Select<? extends Record1<Integer>> maxNumberQuery(CompetitionId competitionId, RobotId robotId) {
        return dsl.select(DSL.max(RUN.NUMBER))
                .from(RUN)
                .where(RUN.COMPETITION_ID.eq(competitionId.getValue()))
                .and(RUN.ROBOT_ID.eq(robotId.getValue()));
    }

    private void insert(Run run) {
        final var record = dsl.newRecord(RUN);
        store(record, run);
    }

    private void update(RunRecord record, Run run) {
        store(record, run);
    }

    private void store(RunRecord record, Run run) {
        runRecordsMapper.update(record, run);
        try {
            record.store();
        } catch (DataChangedException e) {
            throw new EntityHasChangedException(e);
        }
    }
}
