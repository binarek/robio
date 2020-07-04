package binarek.robio.core.domain.robot;

import binarek.robio.codegen.BaseStyle;
import binarek.robio.common.domain.entity.Entity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableRobot.class)
public abstract class Robot implements Entity {

    Robot() {
    }

    @Override
    public final UUID getIdValue() {
        return getId() != null ? getId().getValue() : null;
    }

    @Override
    public final String getNameValue() {
        return getName().getValue();
    }

    @Nullable
    public abstract RobotId getId();

    public abstract RobotName getName();

    public abstract UUID getTeamId();

    @Nullable
    public abstract Weight getWeight();

    @Nullable
    public abstract Length getWidth();

    @Nullable
    public abstract Length getLength();

    @Nullable
    public abstract Length getHeight();
}
