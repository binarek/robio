package binarek.robio.domain.common.entity;

import binarek.robio.domain.common.DomainException;
import org.springframework.lang.Nullable;

import static binarek.robio.domain.common.entity.EntityUtil.name;

public class EntityAlreadyExistsException extends DomainException {

    public EntityAlreadyExistsException(Class<? extends Entity> entityClass, @Nullable Object id, Object name) {
        super(buildMessage(name(entityClass), id, name));
    }

    private static String buildMessage(String entityName, @Nullable Object id, Object name) {
        return id == null ?
                entityName + " with name \"" + name + "\" already exists" :
                entityName + " with id " + id + " or name \"" + name + "\" already exists";
    }
}
