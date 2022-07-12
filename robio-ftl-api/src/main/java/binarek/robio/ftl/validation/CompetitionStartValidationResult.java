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
public abstract class CompetitionStartValidationResult implements BusinessValidationResult<CompetitionStartValidationError> {

    CompetitionStartValidationResult() {
    }

    public static CompetitionStartValidationResult success() {
        return ImmutableCompetitionStartValidationResult.of(Set.of());
    }

    public static CompetitionStartValidationResult error(CompetitionStartValidationError error) {
        return ImmutableCompetitionStartValidationResult.of(Set.of(error));
    }

    public static CompetitionStartValidationResult mergeValidations(Collection<CompetitionStartValidationResult> validations) {
        return ImmutableCompetitionStartValidationResult.of(mergeErrors(validations));
    }

    @Override
    @Value.Parameter
    public abstract Collection<CompetitionStartValidationError> getErrors();
}
