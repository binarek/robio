package binarek.robio.core.domain.robot;

import binarek.robio.common.domain.DomainEntityRepository;

import java.util.UUID;

public interface RobotRepository extends DomainEntityRepository<Robot> {

    boolean existsByTeamId(UUID teamId);
}
