package binarek.robio.ftl.validation;

import binarek.robio.shared.validation.BusinessValidationError;

public interface RunAddValidationError extends BusinessValidationError<RunAddValidationCode> {

    static RunAddValidationError defaultError(RunAddValidationCode code) {
        return RunAddValidationErrorImpl.of(code);
    }
}
