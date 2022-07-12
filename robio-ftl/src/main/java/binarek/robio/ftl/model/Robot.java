package binarek.robio.ftl.model;

import binarek.robio.ftl.validation.*;
import binarek.robio.ftl.validation.CompetitionStartValidationResult;
import binarek.robio.ftl.validation.RunAddValidationResult;
import binarek.robio.ftl.view.RobotView;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;
import binarek.robio.shared.model.RobotName;
import binarek.robio.util.codegen.BaseStyle;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import static binarek.robio.ftl.validation.RunAddValidationCode.ROBOT_DISQUALIFIED;
import static binarek.robio.ftl.validation.RunAddValidationCode.ROBOT_QUALIFICATION_PENDING;

@Value.Immutable
@BaseStyle
public abstract class Robot implements RobotView {

    Robot() {
    }

    public abstract CompetitionId getCompetitionId();

    public abstract RobotId getRobotId();

    @Nullable
    public abstract Long getVersion();

    public abstract RobotName getName();

    public abstract RobotQualification getQualification();

    public final boolean participatesInCompetition() {
        return getQualification() == RobotQualification.QUALIFIED;
    }

    public final RunAddValidationResult checkCanAddRun() {
        return switch (getQualification()) {
            case PENDING -> RunAddValidationResult.error(RunAddValidationError.of(ROBOT_QUALIFICATION_PENDING));
            case DISQUALIFIED -> RunAddValidationResult.error(RunAddValidationError.of(ROBOT_DISQUALIFIED));
            default -> RunAddValidationResult.success();
        };
    }

    public final CompetitionStartValidationResult checkIsReadyToStartCompetition() {
        if (getQualification() == RobotQualification.PENDING) {
            return CompetitionStartValidationResult.error(RobotNotReadyToStartCompetitionValidationError.of(getRobotId()));
        } else {
            return CompetitionStartValidationResult.success();
        }
    }

    public static Robot registerRobot(CompetitionId competitionId, RobotId robotId, RobotName name) {
        return ImmutableRobot.builder()
                .competitionId(competitionId)
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
