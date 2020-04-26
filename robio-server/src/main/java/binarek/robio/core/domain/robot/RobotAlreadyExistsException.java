package binarek.robio.core.domain.robot;

import binarek.robio.common.domain.DomainException;
import org.springframework.lang.Nullable;

import java.util.UUID;

public class RobotAlreadyExistsException extends DomainException {

    public RobotAlreadyExistsException(@Nullable UUID id, String name) {
        super(buildMessage(id, name));
    }

    private static String buildMessage(@Nullable UUID id, String name) {
        return id == null ?
                "Robot with name \"" + name + "\" already exists" :
                "Robot with id " + id + " or name \"" + name + "\" already exists";
    }
}
