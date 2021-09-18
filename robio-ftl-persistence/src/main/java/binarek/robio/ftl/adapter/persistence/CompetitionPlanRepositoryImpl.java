package binarek.robio.ftl.adapter.persistence;

import binarek.robio.ftl.CompetitionPlanRepository;
import binarek.robio.ftl.adapter.persistence.configuration.FtlBeanNames;
import binarek.robio.ftl.adapter.persistence.db.tables.records.CompetitionPlanRecord;
import binarek.robio.ftl.model.CompetitionPlan;
import binarek.robio.shared.model.CompetitionId;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static binarek.robio.ftl.adapter.persistence.db.tables.CompetitionPlan.COMPETITION_PLAN;

@Repository
class CompetitionPlanRepositoryImpl implements CompetitionPlanRepository {

    private final CompetitionPlanRecordsMapper mapper;
    private final AggregateRootEntityTableManager<CompetitionPlanRecord> manager;

    CompetitionPlanRepositoryImpl(@Qualifier(FtlBeanNames.DSL_CONTEXT) DSLContext dsl,
                                  CompetitionPlanRecordsMapper mapper) {
        this.mapper = mapper;
        this.manager = new AggregateRootEntityTableManager<>(dsl, COMPETITION_PLAN, COMPETITION_PLAN.ID, COMPETITION_PLAN.COMPETITION_ID);
    }

    @Override
    @Transactional
    public void save(CompetitionPlan plan) {
        manager.insertOrUpdate(plan.getCompetitionId().getValue(), record -> mapper.update(record, plan));
    }

    @Override
    @Transactional
    public void deleteByCompetitionId(CompetitionId competitionId) {
        manager.deleteByBusinessId(competitionId.getValue());
    }

    @Override
    public boolean existsByCompetitionId(CompetitionId competitionId) {
        return manager.existsByBusinessId(competitionId.getValue());
    }

    @Override
    public Optional<CompetitionPlan> getByCompetitionId(CompetitionId competitionId) {
        return manager.getByBusinessId(competitionId.getValue())
                .map(mapper::toCompetitionPlan);
    }
}
