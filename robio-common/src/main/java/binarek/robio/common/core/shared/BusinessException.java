package binarek.robio.common.core.shared;

public abstract class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message, null, true, false);
    }
}
