package binarek.robio.common.domain;

import org.springframework.lang.Nullable;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @param <E>  entity class
 * @param <ME> minimal domain entity interface
 */
public class DomainEntityServiceHelper<E extends DomainEntity, ME extends DomainEntity> {

    private final DomainEntityRepository<E> entityRepository;
    private final Function<UUID, ? extends DomainEntityNotExistsException> buildNotExistsException;
    private final BiFunction<UUID, String, ? extends DomainEntityAlreadyExistsException> buildAlreadyExistsException;

    public DomainEntityServiceHelper(DomainEntityRepository<E> entityRepository,
                                     Function<UUID, ? extends DomainEntityNotExistsException> buildNotExistsException,
                                     BiFunction<UUID, String, ? extends DomainEntityAlreadyExistsException> buildAlreadyExistsException) {
        this.entityRepository = entityRepository;
        this.buildNotExistsException = buildNotExistsException;
        this.buildAlreadyExistsException = buildAlreadyExistsException;
    }

    public E createEntity(E entity) {
        if (entityRepository.existsByIdOrName(entity.getId(), entity.getName())) {
            throw buildAlreadyExistsException.apply(entity.getId(), entity.getName());
        }
        return entityRepository.insert(entity);
    }

    public E saveEntity(E entity) {
        if (entity.getId() == null && entityRepository.existsByName(entity.getName())) {
            throw buildAlreadyExistsException.apply(entity.getId(), entity.getName());
        }
        return entityRepository.insertOrUpdate(entity);
    }

    public void deleteEntity(UUID id) {
        if (!entityRepository.deleteById(id)) {
            throw buildNotExistsException.apply(id);
        }
    }

    public ME getEntity(UUID id, @Nullable DomainEntityDetailsLevel detailsLevel, Class<? extends ME> resultType) {
        return entityRepository.getById(id, detailsLevel)
                .map(resultType::cast)
                .orElseThrow(() -> buildNotExistsException.apply(id));
    }
}
