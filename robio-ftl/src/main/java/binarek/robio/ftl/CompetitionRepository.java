package binarek.robio.ftl;

import binarek.robio.ftl.model.Competition;
import binarek.robio.shared.model.CompetitionId;

import java.util.Optional;

public interface CompetitionRepository {

    void save(Competition competition);

    boolean existsByCompetitionId(CompetitionId competitionId);

    Optional<Competition> getByCompetitionId(CompetitionId competitionId);
}
