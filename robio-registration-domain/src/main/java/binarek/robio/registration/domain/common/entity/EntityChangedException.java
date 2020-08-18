package binarek.robio.registration.domain.common.entity;

import binarek.robio.registration.domain.common.DomainException;

import static binarek.robio.registration.domain.common.entity.EntityUtil.name;

public class EntityChangedException extends DomainException {

    public EntityChangedException(Class<? extends Entity> entityClass, Object id) {
        super(name(entityClass) + " with id " + id + " has been changed");
    }
}
