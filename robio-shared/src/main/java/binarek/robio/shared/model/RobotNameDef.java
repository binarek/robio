package binarek.robio.shared.model;

import binarek.robio.util.codegen.AbstractSingleValue;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;
import org.springframework.util.Assert;

import static binarek.robio.util.StringUtil.isTrimmed;

@Value.Immutable
@ValueDefStyle
abstract class RobotNameDef extends AbstractSingleValue<String> {

    @Value.Parameter
    @Override
    public abstract String getValue();

    @Value.Check
    protected RobotNameDef normalizeAndValidate() {
        final var name = getValue();
        if (!isTrimmed(name)) {
            return RobotName.of(name.trim());
        } else {
            Assert.state(name.length() >= 3, "Name needs to have at least 3 characters length");
            Assert.state(name.matches("^[\\S ]+$"), "Name contains invalid characters");
            return this;
        }
    }
}
