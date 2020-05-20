package binarek.robio.common.domain.entity;

import binarek.robio.common.persistence.EntityFetchProperties;
import org.springframework.lang.Nullable;

import java.util.Optional;
import java.util.UUID;

public interface EntityRepository<E extends Entity, FP extends EntityFetchProperties> {

    Optional<? extends E> getById(UUID id, @Nullable FP fetchLevel);

    boolean existsByName(String name);

    boolean existsByIdOrName(@Nullable UUID id, String name);

    E insert(E entity);

    E insertOrUpdate(E entity);

    boolean deleteById(UUID id);
}
