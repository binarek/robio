package binarek.robio.common.domain.entity;

import binarek.robio.common.domain.DomainException;

public class EntityInvalidIdentityException extends DomainException {

    public EntityInvalidIdentityException(Object id) {
        super("Entity has invalid id " + id);
    }
}
