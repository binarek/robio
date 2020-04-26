package binarek.robio.core.api.robot;

import binarek.robio.core.domain.robot.RobotAlreadyExistsException;
import binarek.robio.core.domain.robot.RobotNotExistsException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@RestControllerAdvice(assignableTypes = RobotController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RobotExceptionHandler implements ProblemHandling {

    @ExceptionHandler(RobotNotExistsException.class)
    public ResponseEntity<Problem> handleRobotNotExistsException(Exception exception, NativeWebRequest request) {
        return create(Status.NOT_FOUND, exception, request);
    }

    @ExceptionHandler(RobotAlreadyExistsException.class)
    public ResponseEntity<Problem> handleRobotAlreadyExistsException(Exception exception, NativeWebRequest request) {
        return create(Status.BAD_REQUEST, exception, request);
    }
}