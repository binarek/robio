package binarek.robio.ftl;

import binarek.robio.ftl.exception.RobotAlreadyExistsException;
import binarek.robio.shared.model.RobotId;
import org.springframework.stereotype.Service;

@Service
public class RobotService {

    private final RobotRepository robotRepository;

    RobotService(RobotRepository robotRepository) {
        this.robotRepository = robotRepository;
    }

    /**
     * Checks if new robot with given id can be registered
     *
     * @param robotId robot id
     * @throws RobotAlreadyExistsException if robot with id has already been registered
     */
    public void validateIfCanRegisterRobot(RobotId robotId) {
        if (robotRepository.existsByRobotId(robotId)) {
            throw RobotAlreadyExistsException.of(robotId);
        }
    }
}
