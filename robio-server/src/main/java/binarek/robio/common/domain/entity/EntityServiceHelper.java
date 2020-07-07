package binarek.robio.common.domain.entity;

import binarek.robio.common.persistence.EntityFetchProperties;
import org.springframework.lang.Nullable;

/**
 * @param <E> entity class
 */
public class EntityServiceHelper<E extends Entity, FP extends EntityFetchProperties, ID, NAME> {

    private final Class<E> entityClass;
    private final EntityRepository<E, FP, ID, NAME> entityRepository;
    private final EntityValueExtractor<E, ID, NAME> entityValueExtractor;

    public EntityServiceHelper(Class<E> entityClass,
                               EntityRepository<E, FP, ID, NAME> entityRepository,
                               EntityValueExtractor<E, ID, NAME> entityValueExtractor) {
        this.entityClass = entityClass;
        this.entityRepository = entityRepository;
        this.entityValueExtractor = entityValueExtractor;
    }

    public E createEntity(E entity) {
        var id = entityValueExtractor.getId(entity);
        var name = entityValueExtractor.getName(entity);
        if (entityRepository.existsByIdOrName(id, name)) {
            throw new EntityAlreadyExistsException(entityClass, id, name);
        }
        return entityRepository.insert(entity);
    }

    public E saveEntity(E entity) {
        var id = entityValueExtractor.getId(entity);
        var name = entityValueExtractor.getName(entity);
        if (id == null && entityRepository.existsByName(name)) {
            throw new EntityAlreadyExistsException(entityClass, id, name);
        }
        return entityRepository.insertOrUpdate(entity);
    }

    public void deleteEntity(ID id) {
        if (!entityRepository.deleteById(id)) {
            throw new EntityNotExistsException(entityClass, id);
        }
    }

    public E getEntity(ID id) {
        return getEntity(id, null);
    }

    public E getEntity(ID id, @Nullable FP fetchProperties) {
        return entityRepository.getById(id, fetchProperties)
                .orElseThrow(() -> new EntityNotExistsException(entityClass, id));
    }
}
