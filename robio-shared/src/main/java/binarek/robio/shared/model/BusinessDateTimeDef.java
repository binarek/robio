package binarek.robio.shared.model;

import binarek.robio.util.codegen.AbstractSingleValue;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Value.Immutable
@ValueDefStyle
abstract class BusinessDateTimeDef extends AbstractSingleValue<ZonedDateTime> {

    @Value.Parameter
    @Override
    public abstract ZonedDateTime getValue();

    @Value.Check
    protected BusinessDateTimeDef normalize() {
        if (getValue().getNano() == 0) {
            return this;
        } else {
            return BusinessDateTime.of(getValue().truncatedTo(ChronoUnit.SECONDS));
        }
    }
}
