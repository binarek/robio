package binarek.robio.common.domain.entity;

import binarek.robio.common.domain.DomainException;
import org.springframework.lang.Nullable;

import java.util.UUID;

public class EntityAlreadyExistsException extends DomainException {

    public EntityAlreadyExistsException(String entityName, @Nullable UUID id, String name) {
        super(buildMessage(entityName, id, name));
    }

    private static String buildMessage(String entityName, @Nullable UUID id, String name) {
        return id == null ?
                entityName + " with name \"" + name + "\" already exists" :
                entityName + " with id " + id + " or name \"" + name + "\" already exists";
    }
}
