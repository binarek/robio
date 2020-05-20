package binarek.robio.core.domain.robot;

import binarek.robio.common.domain.entity.EntityRepository;

import java.util.UUID;

public interface RobotRepository extends EntityRepository<Robot> {

    boolean existsByTeamId(UUID teamId);
}
