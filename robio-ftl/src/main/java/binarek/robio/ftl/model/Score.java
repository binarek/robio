package binarek.robio.ftl.model;

import binarek.robio.ftl.view.ScoreView;
import binarek.robio.shared.model.RobotId;
import binarek.robio.util.codegen.BaseStyle;
import org.apache.commons.lang3.Validate;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
@BaseStyle
public abstract class Score implements ScoreView {

    Score() {
    }

    @Value.Parameter
    public abstract RobotId getRobotId();

    @Nullable
    @Value.Parameter
    public abstract Integer getPosition();

    @Nullable
    @Value.Parameter
    public abstract RunTime getBestTime();

    public static Score newEmptyScore(RobotId robotId) {
        return ImmutableScore.of(robotId, null, null);
    }

    public static Score newScore(RobotId robotId, Integer position, RunTime bestRunTime) {
        return ImmutableScore.of(robotId, position, bestRunTime);
    }

    @Value.Check
    protected void validate() {
        Validate.isTrue(positionAndBestRuntimeDefinedOrNot(), "Cannot determine if score is empty");
        Validate.isTrue(getPosition() == null || getPosition() > 0, "Position needs to be null or positive");
    }

    private boolean positionAndBestRuntimeDefinedOrNot() {
        return (getPosition() != null && getBestTime() != null) || (getPosition() == null && getBestTime() == null);
    }
}
