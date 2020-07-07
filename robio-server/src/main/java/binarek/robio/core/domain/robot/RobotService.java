package binarek.robio.core.domain.robot;

import binarek.robio.common.domain.entity.EntityNotExistsException;
import binarek.robio.common.domain.entity.EntityServiceHelper;
import binarek.robio.common.domain.entity.EntityValueExtractor;
import binarek.robio.common.persistence.EntityFetchProperties;
import binarek.robio.core.domain.team.Team;
import binarek.robio.core.domain.team.TeamRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class RobotService {

    private final EntityServiceHelper<Robot, EntityFetchProperties.NotSupported, RobotId, RobotName> serviceHelper;
    private final TeamRepository teamRepository;

    public RobotService(RobotRepository robotRepository, TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
        this.serviceHelper = new EntityServiceHelper<>(Robot.class, robotRepository, new RobotValueExtractor());
    }

    public Robot createRobot(Robot robot) {
        if (!teamRepository.existsById(robot.getTeamId())) {
            throw new EntityNotExistsException(Team.class, robot.getTeamId().getValue());
        }
        return serviceHelper.createEntity(robot);
    }

    public Robot saveRobot(Robot robot) {
        return serviceHelper.saveEntity(robot);
    }

    public void deleteRobot(RobotId id) {
        serviceHelper.deleteEntity(id);
    }

    public Robot getRobot(RobotId id) {
        return serviceHelper.getEntity(id);
    }

    private static final class RobotValueExtractor implements EntityValueExtractor<Robot, RobotId, RobotName> {
        @Override
        @Nullable
        public RobotId getId(Robot robot) {
            return robot.getId();
        }

        @Override
        public RobotName getName(Robot robot) {
            return robot.getName();
        }
    }
}
