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
public interface Robot extends Entity {

    @Override
    default String getNameValue() {
        return getName().getValue();
    }

    RobotName getName();

    UUID getTeamId();

    @Nullable
    Weight getWeight();

    @Nullable
    Length getWidth();

    @Nullable
    Length getLength();

    @Nullable
    Length getHeight();
}
