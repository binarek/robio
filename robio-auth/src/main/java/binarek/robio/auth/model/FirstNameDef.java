package binarek.robio.auth.model;

import binarek.robio.util.codegen.AbstractSingleValue;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

import static binarek.robio.shared.constraint.SharedConstraints.firstNameConstraints;
import static binarek.robio.util.StringUtil.isTrimmed;

@Value.Immutable
@ValueDefStyle
abstract class FirstNameDef extends AbstractSingleValue<String> {

    @Value.Parameter
    @Override
    public abstract String getValue();

    @Value.Check
    protected FirstNameDef normalizeAndValidate() {
        final var name = getValue();
        if (!isTrimmed(name)) {
            return FirstName.of(name.trim());
        } else {
            firstNameConstraints().throwExceptionIfNotMatch(name);
            return this;
        }
    }
}
