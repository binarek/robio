package binarek.robio.core.domain.robot;

import binarek.robio.core.domain.team.TeamNotExistsException;
import binarek.robio.core.domain.team.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RobotService {

    private final RobotRepository robotRepository;
    private final TeamRepository teamRepository;

    public RobotService(RobotRepository robotRepository, TeamRepository teamRepository) {
        this.robotRepository = robotRepository;
        this.teamRepository = teamRepository;
    }

    public Robot createRobot(Robot robot) {
        if (robotRepository.existsByIdOrName(robot.getId(), robot.getName())) {
            throw new RobotAlreadyExistsException(robot.getId(), robot.getName());
        }
        if (!teamRepository.existsById(robot.getTeamId())) {
            throw new TeamNotExistsException(robot.getTeamId());
        }
        return robotRepository.insert(robot);
    }

    public Robot saveRobot(Robot robot) {
        if (robot.getId() == null && robotRepository.existsByName(robot.getName())) {
            throw new RobotAlreadyExistsException(robot.getId(), robot.getName());
        }
        return robotRepository.insertOrUpdate(robot);
    }

    public void deleteRobot(UUID id) {
        if (!robotRepository.delete(id)) {
            throw new RobotNotExistsException(id);
        }
    }

    public Robot getRobot(UUID id) {
        return robotRepository.getById(id)
                .orElseThrow(() -> new RobotNotExistsException(id));
    }
}
