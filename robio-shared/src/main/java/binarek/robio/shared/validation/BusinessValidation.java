package binarek.robio.shared.validation;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;

import static binarek.robio.shared.validation.BusinessValidationResult.VALIDATION_ERROR;
import static binarek.robio.shared.validation.BusinessValidationResult.VALIDATION_SUCCESS;

public interface BusinessValidation<T extends BusinessValidationError<?>> {

    Collection<T> getErrors();

    default boolean isSuccess() {
        return getResult() == VALIDATION_SUCCESS;
    }

    default BusinessValidationResult getResult() {
        return CollectionUtils.isEmpty(getErrors()) ? VALIDATION_SUCCESS : VALIDATION_ERROR;
    }
}
