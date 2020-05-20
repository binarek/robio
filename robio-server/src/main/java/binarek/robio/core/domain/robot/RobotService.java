package binarek.robio.core.domain.robot;

import binarek.robio.common.domain.entity.EntityNotExistsException;
import binarek.robio.common.domain.entity.EntityServiceHelper;
import binarek.robio.core.domain.team.Team;
import binarek.robio.core.domain.team.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RobotService {

    private final EntityServiceHelper<Robot, Robot> serviceHelper;
    private final TeamRepository teamRepository;

    public RobotService(RobotRepository robotRepository, TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
        this.serviceHelper = new EntityServiceHelper<>(robotRepository, Robot.ENTITY_NAME);
    }

    public Robot createRobot(Robot robot) {
        if (!teamRepository.existsById(robot.getTeamId())) {
            throw new EntityNotExistsException(Team.ENTITY_NAME, robot.getTeamId());
        }
        return serviceHelper.createEntity(robot);
    }

    public Robot saveRobot(Robot robot) {
        return serviceHelper.saveEntity(robot);
    }

    public void deleteRobot(UUID id) {
        serviceHelper.deleteEntity(id);
    }

    public Robot getRobot(UUID id) {
        return serviceHelper.getEntity(id, null, Robot.class);
    }
}
