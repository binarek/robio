package binarek.robio.ftl.exception;

import binarek.robio.shared.exception.BusinessException;
import binarek.robio.shared.model.CompetitionId;

public class CompetitionPlanAlreadyInitializedException extends BusinessException {

    private CompetitionPlanAlreadyInitializedException(String message) {
        super(message);
    }

    public static CompetitionPlanAlreadyInitializedException of(CompetitionId competitionId) {
        return new CompetitionPlanAlreadyInitializedException("Competition plan for id " + competitionId + " already exists");
    }
}
