package binarek.robio.ftl.exception;

import binarek.robio.shared.exception.BusinessException;
import binarek.robio.shared.model.RobotId;

public class RobotAlreadyExistsException extends BusinessException {

    private RobotAlreadyExistsException(String message) {
        super(message);
    }

    public static RobotAlreadyExistsException of(RobotId robotId) {
        return new RobotAlreadyExistsException("Robot with id " + robotId + " already exists");
    }
}
