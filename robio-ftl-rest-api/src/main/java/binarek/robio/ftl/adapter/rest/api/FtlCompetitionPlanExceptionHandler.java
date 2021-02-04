package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.exception.CompetitionPlanAlreadyExistsException;
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
    ResponseEntity<Problem> handleCompetitionPlanNotFoundException(Exception exception, NativeWebRequest request) {
        return create(Status.NOT_FOUND, exception, request);
    }

    @ExceptionHandler(CompetitionPlanAlreadyExistsException.class)
    ResponseEntity<Problem> handleCompetitionPlanAlreadyExistsException(Exception exception, NativeWebRequest request) {
        return create(Status.CONFLICT, exception, request);
    }
}
