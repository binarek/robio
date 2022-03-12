package binarek.robio.auth.model;

import binarek.robio.util.codegen.AbstractSingleValue;
import org.apache.commons.lang3.Validate;
import org.immutables.value.Value;

import static binarek.robio.util.StringUtil.isTrimmed;

@Value.Immutable
@Value.Style(visibility = Value.Style.ImplementationVisibility.PACKAGE)
public abstract class Username extends AbstractSingleValue<String> {

    Username() {
    }

    @Override
    @Value.Parameter
    public abstract String getValue();

    @Value.Check
    protected Username normalizeAndValidate() {
        final var username = getValue();
        if (!isTrimmed(username)) {
            return ImmutableUsername.of(username.trim());
        } else {
            Validate.isTrue(isValidUsername(getValue()), "%s is not valid username", getValue());
            return this;
        }
    }

    public static Username of(String value) {
        return ImmutableUsername.of(value);
    }

    public static boolean isValidUsername(String usernameValue) {
        return usernameValue.matches("^[a-z][a-z0-9]{2,}$");
    }
}
