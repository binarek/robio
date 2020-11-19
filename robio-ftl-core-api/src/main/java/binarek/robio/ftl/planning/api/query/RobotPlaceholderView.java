package binarek.robio.ftl.planning.api.query;

import org.springframework.lang.Nullable;

import java.util.UUID;

public interface RobotPlaceholderView {

    UUID getTeamId();

    @Nullable
    UUID getRobotId();
}
