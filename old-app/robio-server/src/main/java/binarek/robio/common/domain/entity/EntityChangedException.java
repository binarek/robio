package binarek.robio.common.domain.entity;

import binarek.robio.common.domain.DomainException;

import static binarek.robio.common.domain.entity.EntityUtil.name;

public class EntityChangedException extends DomainException {

    public EntityChangedException(Class<? extends Entity> entityClass, Object id) {
        super(name(entityClass) + " with id " + id + " has been changed");
    }
}
