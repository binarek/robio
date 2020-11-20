package binarek.robio.ftl.planning.view;

import org.springframework.lang.Nullable;

import java.util.UUID;

public interface RobotPlaceholderView {

    UUID getTeamId();

    @Nullable
    UUID getRobotId();
}
