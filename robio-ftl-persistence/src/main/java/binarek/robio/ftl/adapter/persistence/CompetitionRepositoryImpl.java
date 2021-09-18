package binarek.robio.ftl.adapter.persistence;

import binarek.robio.ftl.CompetitionRepository;
import binarek.robio.ftl.adapter.persistence.configuration.FtlBeanNames;
import binarek.robio.ftl.adapter.persistence.db.tables.records.CompetitionRecord;
import binarek.robio.ftl.model.Competition;
import binarek.robio.shared.model.CompetitionId;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static binarek.robio.ftl.adapter.persistence.db.tables.Competition.COMPETITION;

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
        manager.insertOrUpdate(competition.getCompetitionId().getValue(), record -> mapper.update(record, competition));
    }

    @Override
    public boolean existsByCompetitionId(CompetitionId competitionId) {
        return manager.existsByBusinessId(competitionId.getValue());
    }

    @Override
    public Optional<Competition> getByCompetitionId(CompetitionId competitionId) {
        return manager.getByBusinessId(competitionId.getValue())
                .map(mapper::toCompetition);
    }
}
