package binarek.robio.domain.common;

public abstract class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message, null, true, false);
    }
}
