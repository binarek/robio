package binarek.robio.common.domain.entity;

import binarek.robio.common.domain.DomainException;

import java.util.UUID;

import static binarek.robio.common.domain.entity.EntityUtil.name;

public class EntityChangedException extends DomainException {

    public EntityChangedException(Class<? extends Entity> entityClass, UUID id) {
        super(name(entityClass) + " with id " + id + " has been changed");
    }
}
