package binarek.robio.ftl.planning.view;

import org.springframework.lang.Nullable;

import java.util.List;
import java.util.UUID;

public interface CompetitionPlanView {

    UUID getCompetitionId();

    List<? extends RobotPlaceholderView> getRobots();

    @Nullable
    Integer getRunsLimitPerRobot();
}
