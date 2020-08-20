package binarek.robio.common.domain.value;

import binarek.robio.common.codegen.ValueTypeStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = ImmutableSortOrder.class)
public abstract class SortOrder<SF> {

    public static <SF> SortOrder<SF> of(SF property, Direction direction) {
        return ImmutableSortOrder.ofValue(property, direction);
    }

    @Value.Parameter
    public abstract SF getProperty();

    @Value.Parameter
    public abstract Direction getDirection();

    public enum Direction {ASC, DESC}
}
