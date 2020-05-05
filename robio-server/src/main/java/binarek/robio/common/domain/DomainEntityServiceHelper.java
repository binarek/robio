package binarek.robio.common.domain;

import org.springframework.lang.Nullable;

import java.util.UUID;

/**
 * @param <E>  entity class
 * @param <ME> minimal domain entity interface
 */
public class DomainEntityServiceHelper<E extends DomainEntity, ME extends DomainEntity> {

    private final DomainEntityRepository<E> entityRepository;
    private final String entityName;

    public DomainEntityServiceHelper(DomainEntityRepository<E> entityRepository,
                                     String entityName) {
        this.entityRepository = entityRepository;
        this.entityName = entityName;
    }

    public E createEntity(E entity) {
        if (entityRepository.existsByIdOrName(entity.getId(), entity.getName())) {
            throw new DomainEntityAlreadyExistsException(entityName, entity.getId(), entity.getName());
        }
        return entityRepository.insert(entity);
    }

    public E saveEntity(E entity) {
        if (entity.getId() == null && entityRepository.existsByName(entity.getName())) {
            throw new DomainEntityAlreadyExistsException(entityName, entity.getId(), entity.getName());
        }
        return entityRepository.insertOrUpdate(entity);
    }

    public void deleteEntity(UUID id) {
        if (!entityRepository.deleteById(id)) {
            throw new DomainEntityNotExistsException(entityName, id);
        }
    }

    public ME getEntity(UUID id, @Nullable DomainEntityDetailsLevel detailsLevel, Class<? extends ME> resultType) {
        return entityRepository.getById(id, detailsLevel)
                .map(resultType::cast)
                .orElseThrow(() -> new DomainEntityNotExistsException(entityName, id));
    }
}
