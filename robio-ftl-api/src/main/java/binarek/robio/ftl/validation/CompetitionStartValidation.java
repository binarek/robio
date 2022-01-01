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
public abstract class CompetitionStartValidation implements BusinessValidation<CompetitionStartValidationError> {

    CompetitionStartValidation() {
    }

    public static CompetitionStartValidation success() {
        return ImmutableCompetitionStartValidation.of(Set.of());
    }

    public static CompetitionStartValidation error(CompetitionStartValidationError error) {
        return ImmutableCompetitionStartValidation.of(Set.of(error));
    }

    public static CompetitionStartValidation mergeValidations(Collection<CompetitionStartValidation> validations) {
        return ImmutableCompetitionStartValidation.of(mergeErrors(validations));
    }

    @Override
    @Value.Parameter
    public abstract Collection<CompetitionStartValidationError> getErrors();
}
