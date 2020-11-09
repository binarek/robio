package binarek.robio.common.core.shared;

public abstract class AggregateRootAlreadyExistsException extends BusinessException {

    public AggregateRootAlreadyExistsException(String message) {
        super(message);
    }
}
