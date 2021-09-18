package binarek.robio.ftl.validation;

import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

@Value.Immutable
@ValueDefStyle
abstract class NotEnoughRobotsToStartCompetitionValidationErrorDef implements CompetitionStartValidationError {

    @Value.Parameter
    public abstract Integer getMinRobotsToStartCompetition();

    @Value.Parameter
    public abstract Integer getRobotsNumber();

    @Override
    public final CompetitionStartValidationCode getCode() {
        return CompetitionStartValidationCode.NOT_ENOUGH_ROBOTS;
    }
}
