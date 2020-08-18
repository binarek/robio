package binarek.robio.registration.domain.common.entity;

import org.springframework.lang.Nullable;

public interface EntityValueExtractor<E extends Entity, ID, NAME> {

    @Nullable
    ID getId(E entity);

    NAME getName(E entity);
}
