package binarek.robio.shared.model;

import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

import static binarek.robio.shared.constraint.SharedConstraints.usernameConstraints;
import static binarek.robio.util.StringUtil.isTrimmed;

@Value.Immutable
@ValueDefStyle
abstract class UserDef {

    @Value.Parameter
    public abstract String getUsername();

    @Value.Check
    protected UserDef normalizeAndValidate() {
        final var name = getUsername();
        if (!isTrimmed(name)) {
            return User.of(name.trim());
        } else {
            usernameConstraints().throwExceptionIfNotMatch(name);
            return this;
        }
    }
}
