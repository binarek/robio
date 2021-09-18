package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.exception.CompetitionPlanAlreadyInitializedException;
import binarek.robio.ftl.exception.CompetitionPlanNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@RestControllerAdvice(assignableTypes = FtlCompetitionPlanController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
class FtlCompetitionPlanExceptionHandler implements ProblemHandling {

    @ExceptionHandler(CompetitionPlanNotFoundException.class)
    ResponseEntity<Problem> handleNotFoundException(Exception exception, NativeWebRequest request) {
        return create(Status.NOT_FOUND, exception, request);
    }

    @ExceptionHandler(CompetitionPlanAlreadyInitializedException.class)
    ResponseEntity<Problem> handleConflictException(Exception exception, NativeWebRequest request) {
        return create(Status.CONFLICT, exception, request);
    }
}
