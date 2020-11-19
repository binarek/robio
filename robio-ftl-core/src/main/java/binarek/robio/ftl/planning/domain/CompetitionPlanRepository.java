package binarek.robio.ftl.planning.domain;

import binarek.robio.ftl.planning.domain.model.CompetitionPlan;
import binarek.robio.ftl.planning.domain.model.ImmutableCompetitionPlan;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface CompetitionPlanRepository {

    void save(CompetitionPlan plan);

    Optional<CompetitionPlan> getByCompetitionId(UUID competitionId);


    // TODO remove below mock
    @Repository
    class CompetitionPlanRepositoryImpl implements CompetitionPlanRepository {

        private final Map<UUID, CompetitionPlan> map = new HashMap<>();

        @Override
        public void save(CompetitionPlan plan) {
            map.put(plan.getCompetitionId(), plan);
        }

        @Override
        public Optional<CompetitionPlan> getByCompetitionId(UUID competitionId) {
            return Optional.ofNullable(map.get(competitionId))
                    .map(competitionPlan -> ImmutableCompetitionPlan.builder()
                            .from(competitionPlan)
                            .version(10L)
                            .build());
        }
    }
}
