package binarek.robio.domain.common.entity;

import binarek.robio.domain.common.DomainException;

import static binarek.robio.domain.common.entity.EntityUtil.name;

public class EntityChangedException extends DomainException {

    public EntityChangedException(Class<? extends Entity> entityClass, Object id) {
        super(name(entityClass) + " with id " + id + " has been changed");
    }
}
