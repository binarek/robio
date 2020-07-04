package binarek.robio.core.domain.robot;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

import static binarek.robio.common.util.MapperUtil.mapNullSafe;

@Component
public class RobotValueMapper {

    private final LengthUnit DEFAULT_LENGTH_UNIT = LengthUnit.M;
    private final WeightUnit DEFAULT_WEIGHT_UNIT = WeightUnit.G;

    @Nullable
    public RobotId toRobotId(@Nullable UUID id) {
        return mapNullSafe(id, RobotId::of);
    }

    @Nullable
    public UUID toValue(@Nullable RobotId id) {
        return mapNullSafe(id, RobotId::getValue);
    }

    @Nullable
    public RobotName toRobotName(@Nullable String name) {
        return mapNullSafe(name, RobotName::of);
    }

    @Nullable
    public String toValue(@Nullable RobotName name) {
        return mapNullSafe(name, RobotName::getValue);
    }

    @Nullable
    public Length toLength(@Nullable BigDecimal length) {
        return mapNullSafe(length, value -> Length.of(value, DEFAULT_LENGTH_UNIT));
    }

    @Nullable
    public BigDecimal toValue(@Nullable Length length) {
        return mapNullSafe(length, value -> value.convertUnit(DEFAULT_LENGTH_UNIT).getValue());
    }

    @Nullable
    public Weight toWeight(@Nullable BigDecimal weight) {
        return mapNullSafe(weight, value -> Weight.of(value, DEFAULT_WEIGHT_UNIT));
    }

    @Nullable
    public BigDecimal toValue(@Nullable Weight weight) {
        return mapNullSafe(weight, value -> value.convertUnit(DEFAULT_WEIGHT_UNIT).getValue());
    }
}
