package binarek.robio.common.domain;

import java.util.UUID;

public class DomainEntityNotExistsException extends DomainException {

    public DomainEntityNotExistsException(String entityName, UUID id) {
        super(entityName + " with id " + id + " does not exist");
    }
}
