package binarek.robio.ftl.exception;

import binarek.robio.shared.exception.BusinessException;
import binarek.robio.shared.model.CompetitionId;

public class CompetitionAlreadyExistsException extends BusinessException {

    private CompetitionAlreadyExistsException(String message) {
        super(message);
    }

    public static CompetitionAlreadyExistsException of(CompetitionId competitionId) {
        return new CompetitionAlreadyExistsException("Competition for id " + competitionId + " already exists");
    }
}
