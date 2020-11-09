package binarek.robio.ftl.domain.execution;

import binarek.robio.ftl.domain.execution.model.FtlCompetition;

import java.util.Optional;
import java.util.UUID;

public interface FtlCompetitionRepository {

    void save(FtlCompetition ftlCompetition);

    Optional<FtlCompetition> getByCompetitionId(UUID competitionId);
}
