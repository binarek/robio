package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.exception.CompetitionAlreadyStartedException;
import binarek.robio.ftl.exception.CompetitionNotFoundException;
import binarek.robio.ftl.exception.RobotAlreadyRegisteredException;
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
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

@RestControllerAdvice(assignableTypes = FtlRobotCompetitionController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
class FtlRobotExceptionHandler implements ProblemHandling, SecurityAdviceTrait {

    @ExceptionHandler({RobotNotFoundException.class, CompetitionNotFoundException.class})
    ResponseEntity<Problem> handleNotFoundException(Exception exception, NativeWebRequest request) {
        return create(Status.NOT_FOUND, exception, request);
    }

    @ExceptionHandler(RobotAlreadyRegisteredException.class)
    ResponseEntity<Problem> handleConflictException(Exception exception, NativeWebRequest request) {
        return create(Status.CONFLICT, exception, request);
    }

    @ExceptionHandler(CompetitionAlreadyStartedException.class)
    ResponseEntity<Problem> handleCompetitionAlreadyStartedException(Exception exception, NativeWebRequest request) {
        return create(Status.UNPROCESSABLE_ENTITY, exception, request);
    }
}
