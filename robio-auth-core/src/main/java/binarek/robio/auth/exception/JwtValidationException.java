package binarek.robio.auth.exception;

import binarek.robio.shared.exception.BusinessException;

public class JwtValidationException extends BusinessException {

    public JwtValidationException(String message) {
        super(message);
    }
}
