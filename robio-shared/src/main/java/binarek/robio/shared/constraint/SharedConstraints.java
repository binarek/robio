package binarek.robio.shared.constraint;

import org.apache.commons.validator.routines.EmailValidator;

import static binarek.robio.shared.constraint.StringConstraintsImpl.Constraint.*;

public class SharedConstraints {

    private static final Constraints<String> USERNAME = StringConstraintsImpl.of(
            isTrimmed("Username needs to be trimmed"),
            matchesRegex("^[a-z][a-z0-9]{2,}$", "Username is not valid")
    );

    private static final Constraints<String> EMAIL = StringConstraintsImpl.of(
            isTrue(value -> EmailValidator.getInstance().isValid(value), "Invalid email format")
    );

    private static final Constraints<String> FIRST_NAME = StringConstraintsImpl.of(
            isTrimmed("First name needs to be trimmed"),
            isTrue(value -> value.length() >= 2, "First name must have at least 2 characters length"),
            matchesRegex("^[\\S ]+$", "First name cannot contain any whitespace except space"),
            matchesRegex("^[^0-9!@#$%^&*()_=+\\[\\]{};:\",<.>/?]+$", "First name contains invalid non-whitespace character")
    );

    private static final Constraints<String> LAST_NAME = StringConstraintsImpl.of(
            isTrimmed("Last name needs to be trimmed"),
            isTrue(value -> value.length() >= 2, "Last name must have at least 2 characters length"),
            matchesRegex("^[\\S ]+$", "Last name cannot contain any whitespace except space"),
            matchesRegex("^[^0-9!@#$%^&*()_=+\\[\\]{};:\",<.>/?]+$", "Last name contains invalid non-whitespace character")
    );

    private SharedConstraints() {
    }

    public static Constraints<String> usernameConstraints() {
        return USERNAME;
    }

    public static Constraints<String> emailConstraints() {
        return EMAIL;
    }

    public static Constraints<String> firstNameConstraints() {
        return FIRST_NAME;
    }

    public static Constraints<String> lastNameConstraints() {
        return LAST_NAME;
    }
}
