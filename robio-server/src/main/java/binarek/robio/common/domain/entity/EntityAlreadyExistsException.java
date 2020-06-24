package binarek.robio.common.domain.entity;

import binarek.robio.common.domain.DomainException;
import org.springframework.lang.Nullable;

import java.util.UUID;

import static binarek.robio.common.domain.entity.EntityUtil.name;

public class EntityAlreadyExistsException extends DomainException {

    public EntityAlreadyExistsException(Class<? extends Entity> entityClass, @Nullable UUID id, String name) {
        super(buildMessage(name(entityClass), id, name));
    }

    private static String buildMessage(String entityName, @Nullable UUID id, String name) {
        return id == null ?
                entityName + " with name \"" + name + "\" already exists" :
                entityName + " with id " + id + " or name \"" + name + "\" already exists";
    }
}
