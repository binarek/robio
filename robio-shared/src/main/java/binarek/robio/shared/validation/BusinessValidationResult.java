package binarek.robio.shared.validation;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;

public interface BusinessValidationResult<T extends BusinessValidationError<?>> {

    Collection<T> getErrors();

    default boolean isError() {
        return CollectionUtils.isNotEmpty(getErrors());
    }
}
