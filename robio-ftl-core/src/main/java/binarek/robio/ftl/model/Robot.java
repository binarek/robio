package binarek.robio.ftl.model;

import binarek.robio.ftl.validation.CompetitionStartValidation;
import binarek.robio.ftl.validation.RobotNotReadyToStartCompetitionValidationError;
import binarek.robio.ftl.validation.RunAddValidation;
import binarek.robio.ftl.validation.RunAddValidationError;
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

    public final boolean canParticipateInCompetition() {
        return getQualification() == RobotQualification.QUALIFIED;
    }

    public final RunAddValidation checkCanAddRun() {
        switch (getQualification()) {
            case PENDING:
                return RunAddValidation.error(RunAddValidationError.defaultError(ROBOT_QUALIFICATION_PENDING));
            case DISQUALIFIED:
                return RunAddValidation.error(RunAddValidationError.defaultError(ROBOT_DISQUALIFIED));
            default:
                return RunAddValidation.success();
        }
    }

    public final CompetitionStartValidation checkIsReadyToStartCompetition() {
        if (getQualification() == RobotQualification.PENDING) {
            return CompetitionStartValidation.error(RobotNotReadyToStartCompetitionValidationError.of(getRobotId()));
        } else {
            return CompetitionStartValidation.success();
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
