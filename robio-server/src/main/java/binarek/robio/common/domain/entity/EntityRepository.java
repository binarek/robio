package binarek.robio.common.domain.entity;

import binarek.robio.common.persistence.EntityFetchProperties;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface EntityRepository<E extends Entity, FP extends EntityFetchProperties, ID, NAME> {

    Optional<? extends E> getById(ID id, @Nullable FP fetchProperties);

    boolean existsByName(NAME name);

    boolean existsByIdOrName(@Nullable ID id, NAME name);

    E insert(E entity);

    E insertOrUpdate(E entity);

    boolean deleteById(ID id);
}
