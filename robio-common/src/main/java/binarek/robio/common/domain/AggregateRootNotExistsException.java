package binarek.robio.common.domain;

public abstract class AggregateRootNotExistsException extends DomainException {

    public AggregateRootNotExistsException(String message) {
        super(message);
    }
}
