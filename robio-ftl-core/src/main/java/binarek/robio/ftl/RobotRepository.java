package binarek.robio.ftl;

import binarek.robio.ftl.model.Robot;
import binarek.robio.shared.model.RobotId;

import java.util.Optional;

public interface RobotRepository {

    void save(Robot robot);

    boolean existsByRobotId(RobotId robotId);

    Optional<Robot> getByRobotId(RobotId robotId);
}
