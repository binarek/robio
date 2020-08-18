package binarek.robio.registration.domain.common.entity;

import binarek.robio.registration.domain.common.DomainException;

import static binarek.robio.registration.domain.common.entity.EntityUtil.name;

public class EntityNotExistsException extends DomainException {

    public EntityNotExistsException(Class<? extends Entity> entityClass, Object id) {
        super(name(entityClass) + " with id " + id + " does not exist");
    }

    public EntityNotExistsException(Class<? extends Entity> entityClass, Object id, String notMetCondition) {
        super(name(entityClass) + " with id " + id + " and " + notMetCondition + " does not exist");
    }
}
