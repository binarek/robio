package binarek.robio.core.api.team;

import binarek.robio.core.domain.team.CompetitorBelongsToOtherTeamException;
import binarek.robio.core.domain.team.TeamHasRobotsException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@RestControllerAdvice(assignableTypes = TeamController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TeamExceptionHandler implements ProblemHandling {

    @ExceptionHandler(TeamHasRobotsException.class)
    public ResponseEntity<Problem> handleTeamHasRobotsException(Exception exception, NativeWebRequest request) {
        return create(Status.CONFLICT, exception, request);
    }

    @ExceptionHandler(CompetitorBelongsToOtherTeamException.class)
    public ResponseEntity<Problem> handleCompetitorBelongsToOtherTeamException(Exception exception, NativeWebRequest request) {
        return create(Status.CONFLICT, exception, request);
    }
}
