package binarek.robio.ftl.adapter.persistence;

import binarek.robio.ftl.CompetitionRepository;
import binarek.robio.ftl.adapter.persistence.configuration.FtlBeanNames;
import binarek.robio.ftl.adapter.persistence.db.tables.records.CompetitionRecord;
import binarek.robio.ftl.model.Competition;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static binarek.robio.ftl.adapter.persistence.db.tables.Competition.COMPETITION;
import static binarek.robio.ftl.adapter.persistence.db.tables.CompetitionRobot.COMPETITION_ROBOT;
import static binarek.robio.ftl.adapter.persistence.db.tables.Robot.ROBOT;

@Repository
class CompetitionRepositoryImpl implements CompetitionRepository {

    private final DSLContext dsl;
    private final CompetitionRecordsMapper mapper;
    private final AggregateRootEntityTableManager<CompetitionRecord> manager;

    CompetitionRepositoryImpl(@Qualifier(FtlBeanNames.DSL_CONTEXT) DSLContext dsl,
                              CompetitionRecordsMapper mapper) {
        this.dsl = dsl;
        this.mapper = mapper;
        this.manager = new AggregateRootEntityTableManager<>(dsl, COMPETITION, COMPETITION.ID, COMPETITION.COMPETITION_ID);
    }

    @Override
    @Transactional
    public void save(Competition competition) {
        manager.insertOrUpdate(
                competition.getCompetitionId().getValue(),
                setRecordValuesConsumer(competition),
                deleteRobotsConsumer(),
                insertRobotsConsumer(competition));
    }

    @Override
    public boolean existsByCompetitionId(CompetitionId competitionId) {
        return manager.existsByBusinessId(competitionId.getValue());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Competition> getByCompetitionId(CompetitionId competitionId) {
        return manager.getByBusinessId(competitionId.getValue())
                .map(competitionRecord -> mapper.toCompetition(competitionRecord, fetchRobotIds(competitionRecord.getId())));
    }

    private Consumer<CompetitionRecord> setRecordValuesConsumer(Competition competition) {
        return record -> mapper.update(record, competition);
    }

    private Consumer<Long> deleteRobotsConsumer() {
        return competitionRecordId -> {
            dsl.deleteFrom(COMPETITION_ROBOT)
                    .where(COMPETITION_ROBOT.COMPETITION_ID.eq(competitionRecordId))
                    .execute();
        };
    }

    private Consumer<Long> insertRobotsConsumer(Competition competition) {
        return competitionRecordId -> {
            final var robotIds = competition.getRobots().stream()
                    .map(RobotId::getValue)
                    .collect(Collectors.toSet());

            dsl.insertInto(COMPETITION_ROBOT)
                    .select(dsl.select(DSL.val(competitionRecordId), ROBOT.ID)
                            .from(ROBOT)
                            .where(ROBOT.ROBOT_ID.in(robotIds)))
                    .execute();
        };
    }

    private Collection<UUID> fetchRobotIds(Long competitionRecordId) {
        return dsl.select(ROBOT.ROBOT_ID)
                .from(ROBOT)
                .whereExists(dsl.selectOne()
                        .from(COMPETITION_ROBOT)
                        .where(COMPETITION_ROBOT.COMPETITION_ID.eq(competitionRecordId))
                        .and(COMPETITION_ROBOT.ROBOT_ID.eq(ROBOT.ID)))
                .fetch(Record1::value1);
    }
}
