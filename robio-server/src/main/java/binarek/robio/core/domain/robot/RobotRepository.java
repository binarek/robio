package binarek.robio.core.domain.robot;

import binarek.robio.common.domain.entity.EntityRepository;
import binarek.robio.common.persistence.EntityFetchProperties;

import java.util.List;
import java.util.UUID;

public interface RobotRepository extends EntityRepository<Robot, EntityFetchProperties.NotSupported> {

    boolean existsByTeamId(UUID teamId);

    List<UUID> getIdsByTeamId(UUID teamId);
}
