package binarek.robio.ftl.planning.api.exception;

import binarek.robio.common.exception.BusinessException;

import java.util.UUID;

public class CompetitionPlanNotFoundException extends BusinessException {

    private CompetitionPlanNotFoundException(String message) {
        super(message);
    }

    public static CompetitionPlanNotFoundException ofCompetitionId(UUID competitionId) {
        return new CompetitionPlanNotFoundException("Cannot find plan for competition id: " + competitionId);
    }
}
