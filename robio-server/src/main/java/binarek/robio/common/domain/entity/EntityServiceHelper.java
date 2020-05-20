package binarek.robio.common.domain.entity;

import org.springframework.lang.Nullable;

import java.util.UUID;

/**
 * @param <E>  entity class
 * @param <ME> minimal domain entity interface
 */
public class EntityServiceHelper<E extends ME, ME extends Entity> {

    private final EntityRepository<E> entityRepository;
    private final String entityName;

    public EntityServiceHelper(EntityRepository<E> entityRepository,
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

    public ME getEntity(UUID id, @Nullable EntityDetailsLevel detailsLevel, Class<? extends ME> resultType) {
        return entityRepository.getById(id, detailsLevel)
                .map(resultType::cast)
                .orElseThrow(() -> new EntityNotExistsException(entityName, id));
    }
}
