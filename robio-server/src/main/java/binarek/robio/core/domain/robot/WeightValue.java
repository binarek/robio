package binarek.robio.core.domain.robot;

import binarek.robio.codegen.ValueTypeStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = Weight.class)
abstract class WeightValue {

    @Value.Parameter
    public abstract BigDecimal getValue();

    @Value.Parameter
    public abstract WeightUnit getUnit();

    public Weight convertUnit(WeightUnit targetUnit) {
        if (getUnit() == targetUnit && this instanceof Weight) {
            return (Weight) this;
        }
        return Weight.of(WeightUnit.convertUnit(getValue(), getUnit(), targetUnit), targetUnit);
    }

    @Value.Check
    protected void validate() {
        Assert.state(getValue().signum() >= 0, "Weight value cannot be negative");
    }
}
