package binarek.robio.core.domain.robot;

import binarek.robio.common.domain.entity.EntityRepository;
import binarek.robio.core.domain.team.TeamId;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface RobotRepository extends EntityRepository<Robot, RobotFetchProperties> {

    boolean existsByTeamId(UUID teamId);

    List<UUID> getIdsByTeamId(UUID teamId);

    Map<TeamId, List<RobotId>> getIdsByTeamIds(List<TeamId> ids);
}
