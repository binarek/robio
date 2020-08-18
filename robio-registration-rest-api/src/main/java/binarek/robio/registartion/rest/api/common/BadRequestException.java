package binarek.robio.registartion.rest.api.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadRequestException extends ResponseStatusException {

    public BadRequestException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    @Override
    public String getMessage() {
        return super.getReason();
    }
}
