package binarek.robio.shared.validation;

import java.util.Collection;

import static binarek.robio.shared.validation.BusinessValidationResult.VALIDATION_ERROR;
import static binarek.robio.shared.validation.BusinessValidationResult.VALIDATION_SUCCESS;

public interface BusinessValidation<T extends BusinessValidationError<?>> {

    Collection<T> getErrors();

    default boolean isSuccess() {
        return getResult() == VALIDATION_SUCCESS;
    }

    default BusinessValidationResult getResult() {
        final var errors = getErrors();
        return errors == null || errors.isEmpty() ? VALIDATION_SUCCESS : VALIDATION_ERROR;
    }
}
