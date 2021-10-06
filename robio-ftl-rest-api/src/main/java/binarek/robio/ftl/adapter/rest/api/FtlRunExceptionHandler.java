package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.exception.CompetitionNotFoundException;
import binarek.robio.ftl.exception.RobotNotFoundException;
import binarek.robio.ftl.exception.RunAddValidationException;
import binarek.robio.ftl.exception.RunNotFoundException;
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

@RestControllerAdvice(assignableTypes = FtlRunRobotCompetitionController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
class FtlRunExceptionHandler implements ProblemHandling, SecurityAdviceTrait {

    @ExceptionHandler(RunNotFoundException.class)
    ResponseEntity<Problem> handleNotFoundException(Exception exception, NativeWebRequest request) {
        return create(Status.NOT_FOUND, exception, request);
    }

    @ExceptionHandler({CompetitionNotFoundException.class, RobotNotFoundException.class, RunAddValidationException.class})
    ResponseEntity<Problem> handleUnprocessableEntityException(Exception exception, NativeWebRequest request) {
        // TODO use codes
        return create(Status.UNPROCESSABLE_ENTITY, exception, request);
    }
}
