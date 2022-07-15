package binarek.robio.auth.model;

import binarek.robio.util.codegen.AbstractSingleValue;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

import static binarek.robio.shared.constraint.SharedConstraints.usernameConstraints;
import static binarek.robio.util.StringUtil.isTrimmed;

@Value.Immutable
@ValueDefStyle
abstract class UsernameDef extends AbstractSingleValue<String> {

    @Override
    @Value.Parameter
    public abstract String getValue();

    @Value.Check
    protected UsernameDef normalizeAndValidate() {
        final var username = getValue();
        if (!isTrimmed(username)) {
            return Username.of(username.trim());
        } else {
            usernameConstraints().throwExceptionIfNotMatch(username);
            return this;
        }
    }
}
