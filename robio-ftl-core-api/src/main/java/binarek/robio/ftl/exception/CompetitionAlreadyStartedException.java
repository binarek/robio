package binarek.robio.ftl.exception;

import binarek.robio.shared.exception.BusinessException;
import binarek.robio.shared.model.CompetitionId;

public class CompetitionAlreadyStartedException extends BusinessException {

    private CompetitionAlreadyStartedException(String message) {
        super(message);
    }

    public static CompetitionAlreadyStartedException of(CompetitionId competitionId) {
        return new CompetitionAlreadyStartedException("Competition with id " + competitionId + " already started");
    }
}
