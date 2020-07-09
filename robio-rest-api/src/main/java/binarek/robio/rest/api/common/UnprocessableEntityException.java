package binarek.robio.rest.api.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnprocessableEntityException extends ResponseStatusException {

    public UnprocessableEntityException(String reason) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, reason);
    }

    @Override
    public String getMessage() {
        return super.getReason();
    }
}
