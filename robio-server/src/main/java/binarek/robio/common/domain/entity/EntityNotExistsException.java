package binarek.robio.common.domain.entity;

import binarek.robio.common.domain.DomainException;

import static binarek.robio.common.domain.entity.EntityUtil.name;

public class EntityNotExistsException extends DomainException {

    public EntityNotExistsException(Class<? extends Entity> entityClass, Object id) {
        super(name(entityClass) + " with id " + id + " does not exist");
    }

    public EntityNotExistsException(Class<? extends Entity> entityClass, Object id, String notMetCondition) {
        super(name(entityClass) + " with id " + id + " and " + notMetCondition + " does not exist");
    }
}
