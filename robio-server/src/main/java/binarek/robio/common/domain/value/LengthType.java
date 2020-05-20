package binarek.robio.common.domain.value;

import binarek.robio.codegen.ValueTypeStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = Length.class)
interface LengthType {

    @Value.Parameter
    BigDecimal getValue();

    @Value.Parameter
    LengthUnit getUnit();

    default Length convertUnit(LengthUnit targetUnit) {
        if (getUnit() == targetUnit && this instanceof Length) {
            return (Length) this;
        }
        return Length.of(LengthUnit.convertUnit(getValue(), getUnit(), targetUnit), targetUnit);
    }

    @Value.Check
    default void validate() {
        Assert.state(getValue().signum() >= 0, "Length value cannot be negative");
    }
}
