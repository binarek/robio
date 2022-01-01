package binarek.robio.ftl.validation;

import binarek.robio.shared.validation.BusinessValidation;
import binarek.robio.util.codegen.BaseStyle;
import org.immutables.value.Value;

import java.util.Collection;
import java.util.Set;

import static binarek.robio.shared.validation.BusinessValidationUtil.mergeErrors;

@Value.Immutable
@BaseStyle
@Value.Style(visibility = Value.Style.ImplementationVisibility.PACKAGE, defaults = @Value.Immutable(builder = false, copy = false))
public abstract class RunAddValidation implements BusinessValidation<RunAddValidationError> {

    RunAddValidation() {
    }

    public static RunAddValidation success() {
        return ImmutableRunAddValidation.of(Set.of());
    }

    public static RunAddValidation error(RunAddValidationError error) {
        return ImmutableRunAddValidation.of(Set.of(error));
    }

    public static RunAddValidation mergeValidations(RunAddValidation... validations) {
        return ImmutableRunAddValidation.of(mergeErrors(validations));
    }

    @Override
    @Value.Parameter
    public abstract Collection<RunAddValidationError> getErrors();
}
