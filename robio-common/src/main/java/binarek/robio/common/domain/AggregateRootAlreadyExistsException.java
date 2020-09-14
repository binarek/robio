package binarek.robio.common.domain;

public abstract class AggregateRootAlreadyExistsException extends DomainException {

    public AggregateRootAlreadyExistsException(String message) {
        super(message);
    }
}
