package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.exception.RobotAlreadyExistsException;
import binarek.robio.ftl.exception.RobotNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@RestControllerAdvice(assignableTypes = FtlRobotController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
class FtlRobotExceptionHandler implements ProblemHandling {

    @ExceptionHandler(RobotNotFoundException.class)
    ResponseEntity<Problem> handleRobotNotFoundException(Exception exception, NativeWebRequest request) {
        return create(Status.NOT_FOUND, exception, request);
    }

    @ExceptionHandler(RobotAlreadyExistsException.class)
    ResponseEntity<Problem> handleRobotAlreadyExistsException(Exception exception, NativeWebRequest request) {
        return create(Status.CONFLICT, exception, request);
    }
}
