package binarek.robio.domain.robot;

import binarek.robio.util.codegen.ValueTypeStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = ImmutableLength.class)
public abstract class Length {

    Length() {
    }

    public static Length of(BigDecimal value, LengthUnit unit) {
        return ImmutableLength.ofValue(value, unit);
    }

    @Value.Parameter
    public abstract BigDecimal getValue();

    @Value.Parameter
    public abstract LengthUnit getUnit();

    public Length convertUnit(LengthUnit targetUnit) {
        if (getUnit() == targetUnit) {
            return this;
        }
        return Length.of(LengthUnit.convertUnit(getValue(), getUnit(), targetUnit), targetUnit);
    }

    @Value.Check
    protected void validate() {
        Assert.state(getValue().signum() >= 0, "Length value cannot be negative");
    }
}
