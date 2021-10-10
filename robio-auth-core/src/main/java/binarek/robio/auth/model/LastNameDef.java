package binarek.robio.auth.model;

import binarek.robio.util.codegen.AbstractSingleValue;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;
import org.springframework.util.Assert;

import static binarek.robio.util.StringUtil.isTrimmed;

@Value.Immutable
@ValueDefStyle
abstract class LastNameDef extends AbstractSingleValue<String> {

    @Value.Parameter
    @Override
    public abstract String getValue();

    @Value.Check
    protected LastNameDef normalizeAndValidate() {
        final var name = getValue();
        if (!isTrimmed(name)) {
            return LastName.of(name.trim());
        } else {
            Assert.state(name.length() >= 2, "Last name must have at least 2 characters length");
            Assert.state(name.matches("^[\\S ]+$"), "Last name cannot contain any whitespace except space");
            Assert.state(name.matches("^[^0-9!@#$%^&*()_=+\\[\\]{};:\",<.>/?]*$"), "Last name contains invalid character");
            return this;
        }
    }
}
