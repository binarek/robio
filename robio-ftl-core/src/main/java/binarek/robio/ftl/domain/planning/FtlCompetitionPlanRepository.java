package binarek.robio.ftl.domain.planning;

import binarek.robio.ftl.domain.planning.model.FtlCompetitionPlan;

import java.util.Optional;
import java.util.UUID;

public interface FtlCompetitionPlanRepository {

    void save(FtlCompetitionPlan plan);

    Optional<FtlCompetitionPlan> getByCompetitionId(UUID competitionId);
}
