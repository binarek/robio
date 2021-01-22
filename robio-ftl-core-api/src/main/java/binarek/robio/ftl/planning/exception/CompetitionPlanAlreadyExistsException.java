package binarek.robio.ftl.planning.exception;

import binarek.robio.shared.exception.BusinessException;
import binarek.robio.shared.model.CompetitionId;

public class CompetitionPlanAlreadyExistsException extends BusinessException {

    private CompetitionPlanAlreadyExistsException(String message) {
        super(message);
    }

    public static CompetitionPlanAlreadyExistsException ofCompetitionId(CompetitionId competitionId) {
        return new CompetitionPlanAlreadyExistsException("Competition plan for id " + competitionId + " already exists");
    }
}
