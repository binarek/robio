package binarek.robio.ftl.validation;

import binarek.robio.shared.model.RobotId;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

@Value.Immutable
@ValueDefStyle
abstract class RobotCannotStartInCompetitionValidationErrorDef implements CompetitionStartValidationError {

    @Value.Parameter
    public abstract RobotId getRobotId();

    @Override
    public final CompetitionStartValidationCode getCode() {
        return CompetitionStartValidationCode.ROBOT_CANNOT_START;
    }
}
