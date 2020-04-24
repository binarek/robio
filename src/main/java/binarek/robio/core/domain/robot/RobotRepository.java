package binarek.robio.core.domain.robot;

import java.util.Optional;
import java.util.UUID;

public interface RobotRepository {

    Optional<Robot> getById(UUID id);

    Robot insert(Robot robot);
}
