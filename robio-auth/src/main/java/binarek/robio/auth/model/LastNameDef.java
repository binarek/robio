package binarek.robio.auth.model;

import binarek.robio.util.codegen.AbstractSingleValue;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

import static binarek.robio.shared.constraint.SharedConstraints.lastNameConstraints;
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
            lastNameConstraints().throwExceptionIfNotMatch(name);
            return this;
        }
    }
}
