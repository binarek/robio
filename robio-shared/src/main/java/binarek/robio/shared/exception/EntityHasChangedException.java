package binarek.robio.shared.exception;

public class EntityHasChangedException extends RuntimeException {

    public EntityHasChangedException(Exception e) {
        super(e);
    }
}
