package binarek.robio.core.domain.robot;

import binarek.robio.common.domain.DomainException;

import java.util.UUID;

public class RobotNotExistException extends DomainException {

    public RobotNotExistException(UUID id) {
        super("Robot with id " + id + " does not exist");
    }
}
