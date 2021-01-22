package binarek.robio.ftl.planning.exception;

import binarek.robio.shared.exception.BusinessException;
import binarek.robio.shared.model.CompetitionId;

import java.util.UUID;

public class CompetitionPlanNotFoundException extends BusinessException {

    private CompetitionPlanNotFoundException(String message) {
        super(message);
    }

    public static CompetitionPlanNotFoundException ofCompetitionId(CompetitionId competitionId) {
        return new CompetitionPlanNotFoundException("Cannot find plan for competition id: " + competitionId);
    }
}
