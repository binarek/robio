package binarek.robio.shared.constraint;

import binarek.robio.util.StringUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.StringJoiner;
import java.util.function.Predicate;

class StringConstraintsImpl implements Constraints<String> {

    private final Collection<Constraint> constraints;

    private StringConstraintsImpl(Collection<Constraint> constraints) {
        this.constraints = constraints;
    }

    static StringConstraintsImpl of(Constraint... constraints) {
        return new StringConstraintsImpl(Arrays.asList(constraints));
    }

    @Override
    public boolean matchesAll(String value) {
        return constraints.stream()
                .allMatch(constraint -> constraint.matches(value));
    }

    @Override
    public void throwExceptionIfNotMatch(String value) {
        final var errors = constraints.stream()
                .filter(constraint -> constraint.notMatch(value))
                .map(Constraint::errorMessage)
                .toList();

        if (!errors.isEmpty()) {
            final var sj = new StringJoiner("\n- ", "Argument \"" + value + "\" validation failed:\n- ", "");
            errors.forEach(sj::add);
            throw new IllegalArgumentException(sj.toString());
        }
    }

    record Constraint(Predicate<String> validator, String errorMessage) {

        static Constraint isTrimmed(String errorMessage) {
            return new Constraint(StringUtil::isTrimmed, errorMessage);
        }

        static Constraint matchesRegex(String regex, String errorMessage) {
            return new Constraint(value -> value.matches(regex), errorMessage);
        }

        static Constraint isTrue(Predicate<String> validator, String errorMessage) {
            return new Constraint(validator, errorMessage);
        }

        boolean matches(String value) {
            return validator.test(value);
        }

        boolean notMatch(String value) {
            return !matches(value);
        }
    }
}
