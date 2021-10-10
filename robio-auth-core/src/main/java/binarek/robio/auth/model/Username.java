package binarek.robio.auth.model;

import binarek.robio.util.codegen.AbstractSingleValue;
import org.apache.commons.validator.routines.EmailValidator;
import org.immutables.value.Value;
import org.springframework.util.Assert;

import static binarek.robio.util.StringUtil.isTrimmed;

@Value.Immutable
@Value.Style(visibility = Value.Style.ImplementationVisibility.PACKAGE)
public abstract class Username extends AbstractSingleValue<String> {

    private static final String DEFAULT_ADMIN_USERNAME_VALUE = "admin";

    public static final Username DEFAULT_ADMIN_USERNAME = Username.of(DEFAULT_ADMIN_USERNAME_VALUE);

    Username() {
    }

    @Override
    @Value.Parameter
    public abstract String getValue();

    public final boolean isSpecial() {
        return isSpecialUsername(getValue());
    }

    public final boolean isEmail() {
        return isEmailUsername(getValue());
    }

    @Value.Check
    protected Username normalizeAndValidate() {
        final var username = getValue();
        if (!isTrimmed(username)) {
            return ImmutableUsername.of(username.trim());
        } else {
            Assert.state(isValidUsername(getValue()), () -> getValue() + " is not valid username");
            return this;
        }
    }

    public static Username of(String value) {
        return ImmutableUsername.of(value);
    }

    public static boolean isValidUsername(String usernameValue) {
        return isSpecialUsername(usernameValue) || isEmailUsername(usernameValue);
    }

    private static boolean isSpecialUsername(String usernameValue) {
        return DEFAULT_ADMIN_USERNAME_VALUE.equals(usernameValue);
    }

    private static boolean isEmailUsername(String usernameValue) {
        return EmailValidator.getInstance().isValid(usernameValue);
    }
}
