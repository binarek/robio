package binarek.robio.common.domain.entity;

import binarek.robio.common.persistence.EntityFetchProperties;
import org.springframework.lang.Nullable;

import java.util.UUID;

/**
 * @param <E>  entity class
 */
public class EntityServiceHelper<E extends Entity, FE extends EntityFetchProperties> {

    private final EntityRepository<E, FE> entityRepository;
    private final String entityName;

    public EntityServiceHelper(EntityRepository<E, FE> entityRepository,
                               String entityName) {
        this.entityRepository = entityRepository;
        this.entityName = entityName;
    }

    public E createEntity(E entity) {
        if (entityRepository.existsByIdOrName(entity.getId(), entity.getNameValue())) {
            throw new EntityAlreadyExistsException(entityName, entity.getId(), entity.getNameValue());
        }
        return entityRepository.insert(entity);
    }

    public E saveEntity(E entity) {
        if (entity.getId() == null && entityRepository.existsByName(entity.getNameValue())) {
            throw new EntityAlreadyExistsException(entityName, entity.getId(), entity.getNameValue());
        }
        return entityRepository.insertOrUpdate(entity);
    }

    public void deleteEntity(UUID id) {
        if (!entityRepository.deleteById(id)) {
            throw new EntityNotExistsException(entityName, id);
        }
    }

    public E getEntity(UUID id, @Nullable FE fetchLevel) {
        return entityRepository.getById(id, fetchLevel)
                .orElseThrow(() -> new EntityNotExistsException(entityName, id));
    }
}
