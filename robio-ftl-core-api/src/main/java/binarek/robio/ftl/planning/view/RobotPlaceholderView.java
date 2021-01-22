package binarek.robio.ftl.planning.view;

import binarek.robio.shared.model.RobotId;
import binarek.robio.shared.model.TeamId;
import org.springframework.lang.Nullable;

public interface RobotPlaceholderView {

    TeamId getTeamId();

    @Nullable
    RobotId getRobotId();
}
