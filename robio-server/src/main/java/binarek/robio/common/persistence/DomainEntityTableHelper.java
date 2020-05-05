package binarek.robio.common.persistence;

import binarek.robio.common.domain.DomainEntityChangedException;
import org.jooq.*;
import org.jooq.exception.DataChangedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import static binarek.robio.common.persistence.DomainEntityRecordUtil.EXTERNAL_ID_FIELD;
import static binarek.robio.common.persistence.DomainEntityRecordUtil.NAME_FIELD;

public class DomainEntityTableHelper<R extends UpdatableRecord<R>> {

    private static final Logger logger = LoggerFactory.getLogger(DomainEntityTableHelper.class);

    private final DSLContext dsl;
    private final Table<R> table;
    private final String entityName;

    private final Field<UUID> externalIdField;
    private final Field<String> nameField;

    @SuppressWarnings("unchecked")
    public DomainEntityTableHelper(DSLContext dsl, Table<R> table, String entityName) {
        this.dsl = dsl;
        this.table = table;
        this.entityName = entityName;

        this.externalIdField = (Field<UUID>) Objects.requireNonNull(table.field(EXTERNAL_ID_FIELD));
        this.nameField = (Field<String>) Objects.requireNonNull(table.field(NAME_FIELD));
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
        return Optional.ofNullable(dsl.fetchOne(table, externalIdField.eq(externalId)));
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
            condition = condition.and(externalIdField.eq(externalId));
        }
        return existsByCondition(condition);
    }

    private R store(R record, Consumer<R> updateRecord) {
        updateRecord.accept(record);
        if (record.get(externalIdField) == null) {
            record.set(externalIdField, UUID.randomUUID());
        }
        try {
            record.store();
        } catch (DataChangedException e) {
            throw new DomainEntityChangedException(entityName, (UUID) record.get(EXTERNAL_ID_FIELD));
        }
        return record;
    }

    private boolean existsByCondition(Condition condition) {
        return dsl.selectOne()
                .from(table)
                .where(condition)
                .fetchOne() != null;
    }
}
