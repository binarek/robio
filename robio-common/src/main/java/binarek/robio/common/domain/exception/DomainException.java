package binarek.robio.common.domain.exception;

public abstract class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message, null, true, false);
    }
}
