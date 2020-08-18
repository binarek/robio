package binarek.robio.registration.domain.robot;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum WeightUnit {
    MG(0.001),
    G(1),
    KG(1000);

    private final BigDecimal multiplier;

    WeightUnit(double multiplier) {
        this.multiplier = BigDecimal.valueOf(multiplier);
    }

    public static BigDecimal convertUnit(BigDecimal originalWeight, WeightUnit originalUnit, WeightUnit targetUnit) {
        return originalWeight
                .divide(originalUnit.multiplier, RoundingMode.UNNECESSARY)
                .multiply(targetUnit.multiplier);
    }
}
