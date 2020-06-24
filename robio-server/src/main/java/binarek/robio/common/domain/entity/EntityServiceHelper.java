package binarek.robio.common.domain.entity;

import binarek.robio.common.persistence.EntityFetchProperties;
import org.springframework.lang.Nullable;

import java.util.UUID;

/**
 * @param <E> entity class
 */
public class EntityServiceHelper<E extends Entity, FP extends EntityFetchProperties> {

    private final Class<E> entityClass;
    private final EntityRepository<E, FP> entityRepository;

    public EntityServiceHelper(Class<E> entityClass, EntityRepository<E, FP> entityRepository) {
        this.entityClass = entityClass;
        this.entityRepository = entityRepository;
    }

    public E createEntity(E entity) {
        if (entityRepository.existsByIdOrName(entity.getId(), entity.getNameValue())) {
            throw new EntityAlreadyExistsException(entityClass, entity.getId(), entity.getNameValue());
        }
        return entityRepository.insert(entity);
    }

    public E saveEntity(E entity) {
        if (entity.getId() == null && entityRepository.existsByName(entity.getNameValue())) {
            throw new EntityAlreadyExistsException(entityClass, entity.getId(), entity.getNameValue());
        }
        return entityRepository.insertOrUpdate(entity);
    }

    public void deleteEntity(UUID id) {
        if (!entityRepository.deleteById(id)) {
            throw new EntityNotExistsException(entityClass, id);
        }
    }

    public E getEntity(UUID id) {
        return getEntity(id, null);
    }

    public E getEntity(UUID id, @Nullable FP fetchProperties) {
        return entityRepository.getById(id, fetchProperties)
                .orElseThrow(() -> new EntityNotExistsException(entityClass, id));
    }
}
