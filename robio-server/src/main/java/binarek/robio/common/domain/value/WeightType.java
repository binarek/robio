package binarek.robio.common.domain.value;

import binarek.robio.codegen.ValueTypeStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = Weight.class)
interface WeightType {

    @Value.Parameter
    BigDecimal getValue();

    @Value.Parameter
    WeightUnit getUnit();

    default Weight convertUnit(WeightUnit targetUnit) {
        if (getUnit() == targetUnit && this instanceof Weight) {
            return (Weight) this;
        }
        return Weight.of(WeightUnit.convertUnit(getValue(), getUnit(), targetUnit), targetUnit);
    }

    @Value.Check
    default void validate() {
        Assert.state(getValue().signum() >= 0, "Weight value cannot be negative");
    }
}
