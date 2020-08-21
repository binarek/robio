package binarek.robio.common.domain.exception;

public class AggregateRootNotExistsException extends DomainException {

    public AggregateRootNotExistsException(Class<?> aggRootClass, Object id) {
        super(aggRootClass.getSimpleName() + " with id " + id + " does not exist");
    }
}
