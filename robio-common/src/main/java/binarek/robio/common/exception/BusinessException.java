package binarek.robio.common.exception;

public abstract class BusinessException extends RuntimeException {

    protected BusinessException(String message) {
        super(message, null, true, false);
    }
}
