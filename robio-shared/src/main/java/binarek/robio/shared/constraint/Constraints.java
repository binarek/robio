package binarek.robio.shared.constraint;

public interface Constraints<T> {

    boolean matchesAll(T value);

    default void throwExceptionIfNotMatch(T value) {
        if (!matchesAll(value)) {
            throw new IllegalArgumentException("Value " + value + " does not match constraints");
        }
    }
}
