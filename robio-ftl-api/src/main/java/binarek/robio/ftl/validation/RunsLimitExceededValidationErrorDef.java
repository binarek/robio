package binarek.robio.ftl.validation;

import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

@Value.Immutable
@ValueDefStyle
abstract class RunsLimitExceededValidationErrorDef implements RunAddValidationError {

    @Value.Parameter
    public abstract Integer getRunsNumber();

    @Override
    public final RunAddValidationCode getCode() {
        return RunAddValidationCode.RUN_LIMIT_EXCEEDED;
    }
}
