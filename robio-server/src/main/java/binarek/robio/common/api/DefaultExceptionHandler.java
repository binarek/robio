package binarek.robio.common.api;

import binarek.robio.common.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@RestControllerAdvice
public class DefaultExceptionHandler implements ProblemHandling {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Problem> handleDomainException(Exception exception, NativeWebRequest request) {
        return create(Status.BAD_REQUEST, exception, request);
    }

    @ExceptionHandler(DomainEntityNotExistsException.class)
    public ResponseEntity<Problem> handleDomainEntityNotExistsException(Exception exception, NativeWebRequest request) {
        return create(Status.NOT_FOUND, exception, request);
    }

    @ExceptionHandler(DomainEntityAlreadyExistsException.class)
    public ResponseEntity<Problem> handleDomainEntityAlreadyExistsException(Exception exception, NativeWebRequest request) {
        return create(Status.UNPROCESSABLE_ENTITY, exception, request);
    }

    @ExceptionHandler(DomainEntityChangedException.class)
    public ResponseEntity<Problem> handleDomainEntityChangedException(Exception exception, NativeWebRequest request) {
        return create(Status.CONFLICT, exception, request);
    }

    @ExceptionHandler(DomainEntityInvalidIdentityException.class)
    public ResponseEntity<Problem> handleDomainEntityInvalidIdentityException(Exception exception, NativeWebRequest request) {
        return create(Status.UNPROCESSABLE_ENTITY, exception, request);
    }
}
