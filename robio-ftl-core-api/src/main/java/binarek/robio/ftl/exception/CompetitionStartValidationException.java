package binarek.robio.ftl.exception;

import binarek.robio.ftl.validation.CompetitionStartValidation;
import binarek.robio.shared.exception.BusinessException;
import binarek.robio.shared.model.CompetitionId;

public class CompetitionStartValidationException extends BusinessException {

    private final CompetitionStartValidation validation;

    private CompetitionStartValidationException(String message, CompetitionStartValidation validation) {
        super(message);
        this.validation = validation;
    }

    public static CompetitionStartValidationException of(CompetitionId competitionId,
                                                         CompetitionStartValidation validation) {
        return new CompetitionStartValidationException("Competition with id " + competitionId + " cannot start", validation);
    }

    public CompetitionStartValidation getValidation() {
        return validation;
    }
}
