package binarek.robio.core.domain.robot;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RobotService {

    private final RobotRepository robotRepository;

    public RobotService(RobotRepository robotRepository) {
        this.robotRepository = robotRepository;
    }

    public Robot createRobot(Robot robot) {
        if (robotRepository.existsByIdOrName(robot.getId(), robot.getName())) {
            throw new RobotAlreadyExistsException(robot.getId(), robot.getName());
        }
        return robotRepository.insert(robot);
    }

    public Robot saveRobot(Robot robot) {
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
