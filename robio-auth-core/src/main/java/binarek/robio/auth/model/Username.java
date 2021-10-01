package binarek.robio.auth.model;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Arrays;

public interface Username {

    String getValue();

    static Username of(String usernameValue) {
        if (isSpecialUsername(usernameValue)) {
            return SpecialUsername.of(usernameValue);
        } else if (isHumanUsername(usernameValue)) {
            return HumanUsername.of(usernameValue);
        } else {
            throw new IllegalArgumentException("Cannot create username for value: " + usernameValue);
        }
    }

    static boolean isValidUsername(String usernameValue) {
        return isSpecialUsername(usernameValue) || isHumanUsername(usernameValue);
    }

    static boolean isSpecialUsername(String usernameValue) {
        return Arrays.stream(SpecialUsername.values())
                .map(SpecialUsername::getValue)
                .anyMatch(usernameValue::equals);
    }

    static boolean isHumanUsername(String usernameValue) {
        return EmailValidator.getInstance().isValid(usernameValue);
    }
}
