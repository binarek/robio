package binarek.robio.core.domain.team;

import binarek.robio.common.domain.DomainEntityDetailsLevel;
import org.springframework.lang.Nullable;

import java.util.Optional;
import java.util.UUID;

public interface TeamRepository {

    Optional<TeamWithAssociations> getById(UUID id, DomainEntityDetailsLevel domainEntityDetailsLevel);

    boolean existsById(UUID id);

    boolean existsByName(String name);

    boolean existsByIdOrName(@Nullable UUID id, String name);

    Team insert(Team team);

    Team insertOrUpdate(Team team);

    boolean delete(UUID id);
}
