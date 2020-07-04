package binarek.robio.common.domain.entity;

import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EntityRepository<E extends Entity, FP extends EntityFetchProperties<?>> {

    Optional<? extends E> getById(UUID id, @Nullable FP fetchProperties);

    List<? extends E> getAll(@Nullable FP fetchProperties);

    boolean existsByName(String name);

    boolean existsByIdOrName(@Nullable UUID id, String name);

    E insert(E entity);

    E insertOrUpdate(E entity);

    boolean deleteById(UUID id);
}
