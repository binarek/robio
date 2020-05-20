package binarek.robio.core.domain.team;

import binarek.robio.common.domain.entity.EntityRepository;

import java.util.UUID;

public interface TeamRepository extends EntityRepository<Team> {

    boolean existsById(UUID id);
}
