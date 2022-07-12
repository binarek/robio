package binarek.robio.ftl.validation;

import binarek.robio.shared.validation.BusinessValidationResult;
import binarek.robio.util.codegen.BaseStyle;
import org.immutables.value.Value;

import java.util.Collection;
import java.util.Set;

import static binarek.robio.shared.validation.BusinessValidationUtil.mergeErrors;

@Value.Immutable
@BaseStyle
@Value.Style(visibility = Value.Style.ImplementationVisibility.PACKAGE, defaults = @Value.Immutable(builder = false, copy = false))
public abstract class RunAddValidationResult implements BusinessValidationResult<RunAddValidationError> {

    RunAddValidationResult() {
    }

    public static RunAddValidationResult success() {
        return ImmutableRunAddValidationResult.of(Set.of());
    }

    public static RunAddValidationResult error(RunAddValidationError error) {
        return ImmutableRunAddValidationResult.of(Set.of(error));
    }

    public static RunAddValidationResult mergeValidations(RunAddValidationResult... validations) {
        return ImmutableRunAddValidationResult.of(mergeErrors(validations));
    }

    @Override
    @Value.Parameter
    public abstract Collection<RunAddValidationError> getErrors();
}
