package binarek.robio.util.codegen;

public abstract class AbstractSingleValue<T> {

    public abstract T getValue();

    @Override
    public String toString() {
        return getValue().toString();
    }
}
