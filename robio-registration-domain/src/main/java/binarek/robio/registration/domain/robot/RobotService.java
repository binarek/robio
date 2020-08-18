package binarek.robio.registration.domain.robot;

import binarek.robio.registration.domain.common.entity.EntityNotExistsException;
import binarek.robio.registration.domain.common.entity.EntityServiceHelper;
import binarek.robio.registration.domain.common.entity.EntityValueExtractor;
import binarek.robio.registration.domain.team.Team;
import binarek.robio.registration.domain.team.TeamRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RobotService {

    private final EntityServiceHelper<Robot, RobotFetchProperties, RobotId, RobotName> serviceHelper;
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

    public List<? extends Robot> getRobots(RobotFetchProperties fetchProperties) {
        return serviceHelper.getEntities(fetchProperties);
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
