package binarek.robio.common.domain.entity;

import binarek.robio.common.domain.DomainException;

import java.util.UUID;

public class EntityNotExistsException extends DomainException {

    public EntityNotExistsException(String entityName, UUID id) {
        super(entityName + " with id " + id + " does not exist");
    }
}
