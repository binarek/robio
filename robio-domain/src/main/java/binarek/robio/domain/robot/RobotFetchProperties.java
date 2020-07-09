package binarek.robio.domain.robot;

import binarek.robio.domain.common.entity.EntityFetchProperties;
import binarek.robio.domain.common.value.SortOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
@JsonDeserialize(as = ImmutableRobotFetchProperties.class)
public abstract class RobotFetchProperties extends EntityFetchProperties<RobotSortableField> {

    RobotFetchProperties() {
    }

    public static Builder builder() {
        return ImmutableRobotFetchProperties.builder();
    }

    public interface Builder {

        Builder limit(@Nullable Integer limit);

        Builder offset(@Nullable Integer offset);

        Builder sort(@Nullable Iterable<? extends SortOrder<RobotSortableField>> sort);

        RobotFetchProperties build();
    }
}
