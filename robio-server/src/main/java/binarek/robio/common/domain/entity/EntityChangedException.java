package binarek.robio.common.domain.entity;

import binarek.robio.common.domain.DomainException;

import java.util.UUID;

public class EntityChangedException extends DomainException {

    public EntityChangedException(String entityName, UUID id) {
        super(entityName + " with id " + id + " has been changed");
    }
}
