package binarek.robio.ftl.exception;

import binarek.robio.ftl.validation.RunAddValidationResult;
import binarek.robio.shared.exception.BusinessException;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;

public class RunAddValidationException extends BusinessException {

    private final RunAddValidationResult validation;

    private RunAddValidationException(String message, RunAddValidationResult validation) {
        super(message);
        this.validation = validation;
    }

    public static RunAddValidationException of(CompetitionId competitionId, RobotId robotId, RunAddValidationResult validation) {
        return new RunAddValidationException("Cannot add run for robot id " + robotId + " in competition with id " + competitionId, validation);
    }

    public RunAddValidationResult getValidation() {
        return validation;
    }
}
