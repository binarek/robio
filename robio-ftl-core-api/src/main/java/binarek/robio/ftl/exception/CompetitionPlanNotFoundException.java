package binarek.robio.ftl.exception;

import binarek.robio.shared.exception.BusinessException;
import binarek.robio.shared.model.CompetitionId;

public class CompetitionPlanNotFoundException extends BusinessException {

    private CompetitionPlanNotFoundException(String message) {
        super(message);
    }

    public static CompetitionPlanNotFoundException of(CompetitionId competitionId) {
        return new CompetitionPlanNotFoundException("Cannot find plan for competition id: " + competitionId);
    }
}
