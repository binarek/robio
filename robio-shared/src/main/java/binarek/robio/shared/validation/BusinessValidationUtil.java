package binarek.robio.shared.validation;

import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

public final class BusinessValidationUtil {

    private static final int DEFAULT_ERRORS_NUM_PER_VALIDATION = 3;

    private BusinessValidationUtil() {
    }

    public static <C, E extends BusinessValidationError<C>> Collection<E> mergeErrors(BusinessValidation<E>[] validations) {
        return mergeErrors(Arrays.asList(validations));
    }

    public static <C, E extends BusinessValidationError<C>> Collection<E> mergeErrors(Collection<? extends BusinessValidation<E>> validations) {
        if (CollectionUtils.isEmpty(validations)) {
            return Set.of();
        }
        final var errors = new ArrayList<E>(validations.size() * DEFAULT_ERRORS_NUM_PER_VALIDATION);
        for (var validation : validations) {
            errors.addAll(validation.getErrors());
        }
        return Collections.unmodifiableCollection(errors);
    }
}
