package binarek.robio.rest.api.common;

import binarek.robio.domain.common.DomainException;
import binarek.robio.domain.common.entity.EntityAlreadyExistsException;
import binarek.robio.domain.common.entity.EntityChangedException;
import binarek.robio.domain.common.entity.EntityNotExistsException;
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

    @ExceptionHandler(EntityNotExistsException.class)
    public ResponseEntity<Problem> handleEntityNotExistsException(Exception exception, NativeWebRequest request) {
        return create(Status.NOT_FOUND, exception, request);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<Problem> handleEntityAlreadyExistsException(Exception exception, NativeWebRequest request) {
        return create(Status.UNPROCESSABLE_ENTITY, exception, request);
    }

    @ExceptionHandler(EntityChangedException.class)
    public ResponseEntity<Problem> handleEntityChangedException(Exception exception, NativeWebRequest request) {
        return create(Status.CONFLICT, exception, request);
    }
}
