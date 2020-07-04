package binarek.robio.core.domain.robot;

import binarek.robio.codegen.ValueTypeStyle;
import org.immutables.value.Value;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@Value.Immutable
@ValueTypeStyle
abstract class LengthValue {

    @Value.Parameter
    public abstract BigDecimal getValue();

    @Value.Parameter
    public abstract LengthUnit getUnit();

    public Length convertUnit(LengthUnit targetUnit) {
        if (getUnit() == targetUnit && this instanceof Length) {
            return (Length) this;
        }
        return Length.of(LengthUnit.convertUnit(getValue(), getUnit(), targetUnit), targetUnit);
    }

    @Value.Check
    protected void validate() {
        Assert.state(getValue().signum() >= 0, "Length value cannot be negative");
    }
}
