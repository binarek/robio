package binarek.robio.ftl.adapter.persistence;

import binarek.robio.ftl.planning.CompetitionPlanRepository;
import binarek.robio.ftl.planning.model.CompetitionPlan;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CompetitionPlanRepositoryImpl implements CompetitionPlanRepository {

    private final Map<UUID, CompetitionPlan> plansPerCompetitionId = new HashMap<>();   // TODO use database

    @Override
    public void save(CompetitionPlan plan) {
        plansPerCompetitionId.put(plan.getCompetitionId(), plan);
    }

    @Override
    public boolean existsByCompetitionId(UUID competitionId) {
        return plansPerCompetitionId.containsKey(competitionId);
    }

    @Override
    public Optional<CompetitionPlan> getByCompetitionId(UUID competitionId) {
        return Optional.ofNullable(plansPerCompetitionId.get(competitionId));
    }
}
