package binarek.robio.common.persistence;

import binarek.robio.common.domain.entity.Entity;
import binarek.robio.common.domain.entity.EntityChangedException;
import org.jooq.*;
import org.jooq.exception.DataChangedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import static binarek.robio.common.persistence.EntityRecordUtil.EXTERNAL_ID_FIELD;
import static binarek.robio.common.persistence.EntityRecordUtil.NAME_FIELD;

public class EntityTableHelper<R extends UpdatableRecord<R>> {

    private static final Logger logger = LoggerFactory.getLogger(EntityTableHelper.class);

    private final Class<? extends Entity> entityClass;
    private final DSLContext dsl;
    private final Table<R> table;

    private final Field<UUID> externalIdField;
    private final Field<String> nameField;

    @SuppressWarnings("unchecked")
    public EntityTableHelper(Class<? extends Entity> entityClass, DSLContext dsl, Table<R> table) {
        this(entityClass, dsl, table, (Field<String>) Objects.requireNonNull(table.field(NAME_FIELD)));
    }

    @SuppressWarnings("unchecked")
    public EntityTableHelper(Class<? extends Entity> entityClass, DSLContext dsl, Table<R> table,
                             Field<String> nameField) {
        this.entityClass = entityClass;
        this.dsl = dsl;
        this.table = table;

        this.externalIdField = (Field<UUID>) Objects.requireNonNull(table.field(EXTERNAL_ID_FIELD));
        this.nameField = nameField;
    }

    public R insert(Consumer<R> setRecordValues) {
        return store(dsl.newRecord(table), setRecordValues);
    }

    public R insertOrUpdate(@Nullable UUID externalId, Consumer<R> setRecordValues) {
        var record = dsl.fetchOne(table, externalIdField.eq(externalId));
        if (record != null) {
            return store(record, setRecordValues);
        } else {
            return store(dsl.newRecord(table), setRecordValues);
        }
    }

    public boolean deleteByExternalId(UUID externalId) {
        var deletedRecords = dsl.deleteFrom(table)
                .where(externalIdField.eq(externalId))
                .execute();
        if (deletedRecords > 1) {
            logger.error("More than 1 records deleted by external_id!");
        }
        return deletedRecords != 0;
    }

    public Optional<R> getByExternalId(UUID externalId) {
        return dsl.fetchOptional(table, externalIdField.eq(externalId));
    }

    public List<R> getAll(@Nullable Integer limit, @Nullable Integer offset, List<TableField<R, ?>> orderFields) {
        return dsl.selectFrom(table)
                .orderBy(orderFields)
                .limit(limit)
                .offset(offset)
                .fetch();
    }

    public boolean existsByExternalId(UUID externalId) {
        return existsByCondition(externalIdField.eq(externalId));
    }

    public boolean existsByName(String name) {
        return existsByCondition(nameField.eq(name));
    }

    public boolean existsByExternalIdOrName(@Nullable UUID externalId, String name) {
        var condition = nameField.eq(name);
        if (externalId != null) {
            condition = condition.or(externalIdField.eq(externalId));
        }
        return existsByCondition(condition);
    }

    public boolean existsByCondition(Condition condition) {
        return dsl.selectOne()
                .from(table)
                .where(condition)
                .fetchOne() != null;
    }

    private R store(R record, Consumer<R> updateRecord) {
        updateRecord.accept(record);
        if (record.get(externalIdField) == null) {
            record.set(externalIdField, UUID.randomUUID());
        }
        try {
            record.store();
        } catch (DataChangedException e) {
            throw new EntityChangedException(entityClass, (UUID) record.get(EXTERNAL_ID_FIELD));
        }
        return record;
    }
}
