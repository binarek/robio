package binarek.robio.domain.robot;

import binarek.robio.domain.common.entity.EntityRepository;
import binarek.robio.domain.team.TeamId;

import java.util.List;

public interface RobotRepository extends EntityRepository<Robot, RobotFetchProperties, RobotId, RobotName> {

    boolean existsByTeamId(TeamId teamId);

    List<RobotId> getIdsByTeamId(TeamId teamId);
}
