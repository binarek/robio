package binarek.robio.common.core.shared;

public abstract class AggregateRootNotExistsException extends BusinessException {

    public AggregateRootNotExistsException(String message) {
        super(message);
    }
}
