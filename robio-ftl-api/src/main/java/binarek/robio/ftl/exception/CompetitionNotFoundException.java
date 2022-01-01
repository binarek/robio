package binarek.robio.ftl.exception;

import binarek.robio.shared.exception.BusinessException;
import binarek.robio.shared.model.CompetitionId;

public class CompetitionNotFoundException extends BusinessException {

    private CompetitionNotFoundException(String message) {
        super(message);
    }

    public static CompetitionNotFoundException of(CompetitionId competitionId) {
        return new CompetitionNotFoundException("Cannot find competition with competition id " + competitionId);
    }
}
