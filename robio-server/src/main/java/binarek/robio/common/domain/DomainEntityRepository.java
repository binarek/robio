package binarek.robio.common.domain;

import org.springframework.lang.Nullable;

import java.util.Optional;
import java.util.UUID;

public interface DomainEntityRepository<E extends DomainEntity> {

    Optional<? extends E> getById(UUID id, @Nullable DomainEntityDetailsLevel detailsLevel);

    boolean existsByName(String name);

    boolean existsByIdOrName(@Nullable UUID id, String name);

    E insert(E team);

    E insertOrUpdate(E team);

    boolean deleteById(UUID id);
}
