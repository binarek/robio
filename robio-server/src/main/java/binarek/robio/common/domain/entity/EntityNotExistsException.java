package binarek.robio.common.domain.entity;

import binarek.robio.common.domain.DomainException;

import java.util.UUID;

import static binarek.robio.common.domain.entity.EntityUtil.name;

public class EntityNotExistsException extends DomainException {

    public EntityNotExistsException(Class<? extends Entity> entityClass, UUID id) {
        super(name(entityClass) + " with id " + id + " does not exist");
    }
}
