package binarek.robio.ftl.exception;

import binarek.robio.shared.exception.BusinessException;
import binarek.robio.shared.model.CompetitionId;

public class CompetitionAlreadyInitializedException extends BusinessException {

    private CompetitionAlreadyInitializedException(String message) {
        super(message);
    }

    public static CompetitionAlreadyInitializedException of(CompetitionId competitionId) {
        return new CompetitionAlreadyInitializedException("Competition with id " + competitionId + " is already initialized");
    }
}
