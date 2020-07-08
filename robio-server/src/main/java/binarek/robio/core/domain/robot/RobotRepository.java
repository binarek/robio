package binarek.robio.core.domain.robot;

import binarek.robio.common.domain.entity.EntityRepository;
import binarek.robio.core.domain.team.TeamId;

import java.util.List;

public interface RobotRepository extends EntityRepository<Robot, RobotFetchProperties, RobotId, RobotName> {

    boolean existsByTeamId(TeamId teamId);

    List<RobotId> getIdsByTeamId(TeamId teamId);
}
