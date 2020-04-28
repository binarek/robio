package binarek.robio.common.domain;

import java.util.UUID;

public class DomainEntityChangedException extends DomainException {

    public DomainEntityChangedException(String entityName, UUID id) {
        super(entityName + " with id " + id + " has been changed");
    }
}
