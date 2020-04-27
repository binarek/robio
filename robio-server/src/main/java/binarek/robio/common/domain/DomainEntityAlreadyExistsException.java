package binarek.robio.common.domain;

import org.springframework.lang.Nullable;

import java.util.UUID;

public class DomainEntityAlreadyExistsException extends DomainException {

    public DomainEntityAlreadyExistsException(String entityName, @Nullable UUID id, String name) {
        super(buildMessage(entityName, id, name));
    }

    private static String buildMessage(String entityName, @Nullable UUID id, String name) {
        return id == null ?
                entityName + " with name \"" + name + "\" already exists" :
                entityName + " with id " + id + " or name \"" + name + "\" already exists";
    }
}
