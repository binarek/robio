package binarek.robio.core.domain.robot;

import binarek.robio.common.domain.DomainEntityNotExistsException;

import java.util.UUID;

public class RobotNotExistsException extends DomainEntityNotExistsException {

    public RobotNotExistsException(UUID id) {
        super("Robot", id);
    }
}
