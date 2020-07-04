package binarek.robio.core.domain.robot;

import binarek.robio.codegen.ValueTypeStyle;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.util.Assert;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = RobotName.class)
abstract class RobotNameValue {

    @JsonValue
    @Value.Parameter
    public abstract String getValue();

    @Value.Check
    protected void validate() {
        var name = getValue();
        Assert.state(name.trim().length() == name.length(), "Name must not have leading or trailing whitespaces");
        Assert.state(name.length() >= 3, "Name must have at least 3 characters length");
    }
}