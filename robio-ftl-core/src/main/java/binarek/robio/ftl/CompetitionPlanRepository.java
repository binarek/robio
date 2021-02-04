package binarek.robio.ftl;

import binarek.robio.ftl.model.CompetitionPlan;
import binarek.robio.shared.model.CompetitionId;

import java.util.Optional;

public interface CompetitionPlanRepository {

    void save(CompetitionPlan plan);

    boolean existsByCompetitionId(CompetitionId competitionId);

    Optional<CompetitionPlan> getByCompetitionId(CompetitionId competitionId);
}
