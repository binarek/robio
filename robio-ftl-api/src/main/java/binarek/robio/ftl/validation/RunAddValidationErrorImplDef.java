package binarek.robio.ftl.validation;

import org.immutables.value.Value;

@Value.Immutable
@Value.Style(
        typeAbstract = "*Def",
        typeImmutable = "*",
        visibility = Value.Style.ImplementationVisibility.PACKAGE,
        defaults = @Value.Immutable(copy = false)
)
interface RunAddValidationErrorImplDef extends RunAddValidationError {

    @Value.Parameter
    @Override
    RunAddValidationCode getCode();
}
