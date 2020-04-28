package binarek.robio.core.domain.robot;

import binarek.robio.common.domain.DomainEntityChangedException;

import java.util.UUID;

public class RobotChangedException extends DomainEntityChangedException {

    public RobotChangedException(UUID id) {
        super("Robot", id);
    }
}
