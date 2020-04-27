package binarek.robio.core.domain.team;

import binarek.robio.common.domain.DomainEntityRepository;

import java.util.UUID;

public interface TeamRepository extends DomainEntityRepository<Team> {

    boolean existsById(UUID id);
}
