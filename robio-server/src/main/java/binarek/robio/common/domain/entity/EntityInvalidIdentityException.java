package binarek.robio.common.domain.entity;

import binarek.robio.common.domain.DomainException;

import java.util.UUID;

public class EntityInvalidIdentityException extends DomainException {

    public EntityInvalidIdentityException(UUID id) {
        super("Entity has invalid id " + id);
    }
}
