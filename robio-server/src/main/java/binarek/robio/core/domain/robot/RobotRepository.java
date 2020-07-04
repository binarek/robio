package binarek.robio.core.domain.robot;

import binarek.robio.common.domain.entity.EntityRepository;

import java.util.List;
import java.util.UUID;

public interface RobotRepository extends EntityRepository<Robot, RobotFetchProperties> {

    boolean existsByTeamId(UUID teamId);

    List<UUID> getIdsByTeamId(UUID teamId);
}
