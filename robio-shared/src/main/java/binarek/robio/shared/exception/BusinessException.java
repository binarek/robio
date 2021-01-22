package binarek.robio.shared.exception;

public abstract class BusinessException extends RuntimeException {

    protected BusinessException(String message) {
        super(message, null, true, false);
    }
}
