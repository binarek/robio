package binarek.robio.ftl.planning.rest.api;

import binarek.robio.ftl.planning.api.exception.CompetitionPlanNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@RestControllerAdvice(assignableTypes = FtlPlanController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FtlPlanExceptionHandler implements ProblemHandling {

    @ExceptionHandler(CompetitionPlanNotFoundException.class)
    public ResponseEntity<Problem> handleCompetitionPlanNotFoundException(Exception exception, NativeWebRequest request) {
        return create(Status.NOT_FOUND, exception, request);
    }
}
