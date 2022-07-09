package binarek.robio.ftl.view;

import binarek.robio.ftl.model.RunTime;
import binarek.robio.shared.model.RobotId;
import org.springframework.lang.Nullable;

public interface ScoreView {

    RobotId getRobotId();

    @Nullable
    Integer getPosition();

    @Nullable
    RunTime getBestTime();
}
