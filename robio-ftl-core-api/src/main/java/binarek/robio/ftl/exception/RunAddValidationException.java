package binarek.robio.ftl.exception;

import binarek.robio.ftl.validation.RunAddValidation;
import binarek.robio.shared.exception.BusinessException;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;

public class RunAddValidationException extends BusinessException {

    private final RunAddValidation validation;

    private RunAddValidationException(String message, RunAddValidation validation) {
        super(message);
        this.validation = validation;
    }

    public static RunAddValidationException of(CompetitionId competitionId, RobotId robotId, RunAddValidation validation) {
        return new RunAddValidationException("Cannot add run for robot id " + robotId + " in competition with id " + competitionId, validation);
    }

    public RunAddValidation getValidation() {
        return validation;
    }
}
