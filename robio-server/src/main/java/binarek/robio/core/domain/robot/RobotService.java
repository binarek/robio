package binarek.robio.core.domain.robot;

import binarek.robio.common.domain.DomainEntityServiceHelper;
import binarek.robio.core.domain.team.TeamNotExistsException;
import binarek.robio.core.domain.team.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RobotService {

    private final DomainEntityServiceHelper<Robot, Robot> serviceHelper;
    private final TeamRepository teamRepository;

    public RobotService(RobotRepository robotRepository, TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
        this.serviceHelper = new DomainEntityServiceHelper<>(robotRepository,
                RobotNotExistsException::new, RobotAlreadyExistsException::new);
    }

    public Robot createRobot(Robot robot) {
        if (!teamRepository.existsById(robot.getTeamId())) {
            throw new TeamNotExistsException(robot.getTeamId());
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
