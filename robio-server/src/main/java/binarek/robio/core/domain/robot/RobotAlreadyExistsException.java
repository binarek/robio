package binarek.robio.core.domain.robot;

import binarek.robio.common.domain.DomainEntityAlreadyExistsException;
import org.springframework.lang.Nullable;

import java.util.UUID;

public class RobotAlreadyExistsException extends DomainEntityAlreadyExistsException {

    public RobotAlreadyExistsException(@Nullable UUID id, String name) {
        super("Robot", id, name);
    }
}
