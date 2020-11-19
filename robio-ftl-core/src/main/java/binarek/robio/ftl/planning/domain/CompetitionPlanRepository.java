package binarek.robio.ftl.planning.domain;

import binarek.robio.ftl.planning.domain.model.CompetitionPlan;

import java.util.Optional;
import java.util.UUID;

public interface CompetitionPlanRepository {

    void save(CompetitionPlan plan);

    boolean existsByCompetitionId(UUID competitionId);

    Optional<CompetitionPlan> getByCompetitionId(UUID competitionId);
}
