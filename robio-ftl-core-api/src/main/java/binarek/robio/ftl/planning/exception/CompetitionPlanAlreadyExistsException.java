package binarek.robio.ftl.planning.exception;

import binarek.robio.common.exception.BusinessException;

import java.util.UUID;

public class CompetitionPlanAlreadyExistsException extends BusinessException {

    private CompetitionPlanAlreadyExistsException(String message) {
        super(message);
    }

    public static CompetitionPlanAlreadyExistsException ofCompetitionId(UUID competitionId) {
        return new CompetitionPlanAlreadyExistsException("Competition plan for id " + competitionId + " already exists");
    }
}
