package binarek.robio.common.rest;

import binarek.robio.common.core.shared.AggregateRootAlreadyExistsException;
import binarek.robio.common.core.shared.AggregateRootNotExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@RestControllerAdvice
public class DefaultExceptionHandler implements ProblemHandling {

    @ExceptionHandler(AggregateRootNotExistsException.class)
    public ResponseEntity<Problem> handleNotFound(Exception exception, NativeWebRequest request) {
        return create(Status.NOT_FOUND, exception, request);
    }

    @ExceptionHandler(AggregateRootAlreadyExistsException.class)
    public ResponseEntity<Problem> handleUnprocessableEntity(Exception exception, NativeWebRequest request) {
        return create(Status.UNPROCESSABLE_ENTITY, exception, request);
    }
}
