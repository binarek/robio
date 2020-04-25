package binarek.robio.common.persistence;

import binarek.robio.common.domain.DomainEntity;
import org.jooq.*;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public abstract class DomainEntityRepositoryImpl<E extends DomainEntity, R extends UpdatableRecord<R>> {

    private static final String EXTERNAL_ID_FIELD = "EXTERNAL_ID";

    protected final DSLContext dsl;
    protected final Table<R> table;
    protected final DomainEntityRecordMapper<E, R> recordMapper;

    private final Field<UUID> externalIdField;

    @SuppressWarnings("unchecked")
    protected DomainEntityRepositoryImpl(DSLContext dsl,
                                         Table<R> table,
                                         DomainEntityRecordMapper<E, R> recordMapper) {
        this.dsl = dsl;
        this.table = table;
        this.recordMapper = recordMapper;
        this.externalIdField = (Field<UUID>) Objects.requireNonNull(table.field(EXTERNAL_ID_FIELD));
    }

    public E insert(E entity) {
        return store(entity, dsl.newRecord(table));
    }

    public E insertOrUpdate(E entity) {
        var record = dsl.fetchOne(table, externalIdField.eq(entity.getId()));
        if (record != null) {
            return store(entity, record);
        } else {
            return store(entity, dsl.newRecord(table));
        }
    }

    public boolean delete(UUID entityId) {
        var deletedRecords = dsl.deleteFrom(table)
                .where(externalIdField.eq(entityId))
                .execute();
        if (deletedRecords > 1) {
            throw new IllegalStateException("More than 1 records deleted by external_id!");
        }
        return deletedRecords == 1;
    }

    public Optional<E> getById(UUID entityId) {
        return Optional.ofNullable(dsl.fetchOne(table, externalIdField.eq(entityId)))
                .map(recordMapper::toEntity);
    }

    private E store(E entity, R record) {
        recordMapper.updateRecord(record, entity);
        if (record.get(externalIdField) == null) {
            record.set(externalIdField, UUID.randomUUID());
        }
        record.store();
        return recordMapper.toEntity(record);
    }
}
