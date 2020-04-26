package binarek.robio.core.api;

import binarek.robio.common.domain.DomainException;
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
}
