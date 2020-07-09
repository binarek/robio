package binarek.robio.domain.robot;

import binarek.robio.domain.team.TeamId;
import binarek.robio.util.codegen.BaseStyle;
import binarek.robio.domain.common.entity.Entity;
import binarek.robio.domain.common.value.Notes;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableRobot.class)
public abstract class Robot implements Entity {

    Robot() {
    }

    @Nullable
    public abstract RobotId getId();

    @Nullable
    public abstract Long getVersion();

    public abstract RobotName getName();

    public abstract TeamId getTeamId();

    @Nullable
    public abstract Weight getWeight();

    @Nullable
    public abstract Length getWidth();

    @Nullable
    public abstract Length getLength();

    @Nullable
    public abstract Length getHeight();

    @Nullable
    public abstract Notes getNotes();
}
