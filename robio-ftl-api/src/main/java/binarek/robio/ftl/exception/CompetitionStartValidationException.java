package binarek.robio.ftl.exception;

import binarek.robio.ftl.validation.CompetitionStartValidationResult;
import binarek.robio.shared.exception.BusinessException;
import binarek.robio.shared.model.CompetitionId;

public class CompetitionStartValidationException extends BusinessException {

    private final CompetitionStartValidationResult validation;

    private CompetitionStartValidationException(String message, CompetitionStartValidationResult validation) {
        super(message);
        this.validation = validation;
    }

    public static CompetitionStartValidationException of(CompetitionId competitionId,
                                                         CompetitionStartValidationResult validation) {
        return new CompetitionStartValidationException("Competition with id " + competitionId + " cannot start", validation);
    }

    public CompetitionStartValidationResult getValidation() {
        return validation;
    }
}
