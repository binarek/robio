package binarek.robio.ftl.domain.execution.model;

import binarek.robio.common.codegen.ValueTypeStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = ImmutableTime.class)
public abstract class Time implements Comparable<Time> {

    Time() {
    }

    @Value.Parameter
    public abstract BigDecimal seconds();

    public static Time ofSeconds(BigDecimal seconds) {
        return ImmutableTime.ofValue(seconds);
    }

    @Value.Check
    protected void validate() {
        Assert.state(seconds().signum() >= 0, "Time cannot be negative");
    }

    @Override
    public int compareTo(Time other) {
        return seconds().compareTo(other.seconds());
    }
}
