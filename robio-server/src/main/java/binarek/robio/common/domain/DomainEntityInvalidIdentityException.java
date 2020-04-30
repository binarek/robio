package binarek.robio.common.domain;

import java.util.UUID;

public class DomainEntityInvalidIdentityException extends DomainException {

    public DomainEntityInvalidIdentityException(UUID id) {
        super("Entity has invalid id " + id);
    }
}
