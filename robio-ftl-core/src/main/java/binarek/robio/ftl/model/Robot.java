package binarek.robio.ftl.model;

import binarek.robio.ftl.validation.CompetitionStartValidation;
import binarek.robio.ftl.validation.RobotCannotStartInCompetitionValidationError;
import binarek.robio.ftl.view.RobotView;
import binarek.robio.shared.model.RobotId;
import binarek.robio.shared.model.RobotName;
import binarek.robio.util.codegen.BaseStyle;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
@BaseStyle
public abstract class Robot implements RobotView {

    Robot() {
    }

    public abstract RobotId getRobotId();

    @Nullable
    public abstract Long getVersion();

    public abstract RobotName getName();

    public abstract RobotQualification getQualification();

    public final CompetitionStartValidation checkCanStartInCompetition() {
        if (getQualification() != RobotQualification.QUALIFIED) {
            return CompetitionStartValidation.error(RobotCannotStartInCompetitionValidationError.of(getRobotId()));
        } else {
            return CompetitionStartValidation.success();
        }
    }

    public static Robot newRobot(RobotId robotId, RobotName name) {
        return ImmutableRobot.builder()
                .robotId(robotId)
                .name(name)
                .qualification(RobotQualification.PENDING)
                .build();
    }

    public final Robot qualify() {
        return ImmutableRobot.copyOf(this)
                .withQualification(RobotQualification.QUALIFIED);
    }

    public final Robot disqualify() {
        return ImmutableRobot.copyOf(this)
                .withQualification(RobotQualification.DISQUALIFIED);
    }
}
