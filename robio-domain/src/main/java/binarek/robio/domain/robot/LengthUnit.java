package binarek.robio.domain.robot;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum LengthUnit {
    MM(0.01),
    CM(0.1),
    M(1);

    private final BigDecimal multiplier;

    LengthUnit(double multiplier) {
        this.multiplier = BigDecimal.valueOf(multiplier);
    }

    public static BigDecimal convertUnit(BigDecimal originalLength, LengthUnit originalUnit, LengthUnit targetUnit) {
        return originalLength
                .divide(originalUnit.multiplier, RoundingMode.UNNECESSARY)
                .multiply(targetUnit.multiplier);
    }
}
