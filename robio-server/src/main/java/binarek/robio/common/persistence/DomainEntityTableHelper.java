package binarek.robio.common.persistence;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.UpdatableRecord;
import org.jooq.impl.DSL;
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

    private final Field<UUID> externalIdField;
    private final Field<String> nameField;

    @SuppressWarnings("unchecked")
    public DomainEntityTableHelper(DSLContext dsl, Table<R> table) {
        this.dsl = dsl;
        this.table = table;
        this.externalIdField = (Field<UUID>) Objects.requireNonNull(table.field(EXTERNAL_ID_FIELD));
        this.nameField = (Field<String>) Objects.requireNonNull(table.field(NAME_FIELD));
    }

    public R insert(Consumer<R> updateRecord) {
        return store(dsl.newRecord(table), updateRecord);
    }

    public R insertOrUpdate(@Nullable UUID externalId, Consumer<R> updateRecord) {
        var record = dsl.fetchOne(table, externalIdField.eq(externalId));
        if (record != null) {
            return store(record, updateRecord);
        } else {
            return store(dsl.newRecord(table), updateRecord);
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

    public boolean existsByExternalIdOrName(@Nullable UUID externalId, String name) {
        var existsCondition = DSL.exists(dsl.selectOne().from(table).where(nameField.eq(name)));
        if (externalId != null) {
            existsCondition = existsCondition.and(externalIdField.eq(externalId));
        }
        return dsl.select(DSL.val(1))
                .from(table)
                .where(existsCondition)
                .execute() == 1;
    }

    private R store(R record, Consumer<R> updateRecord) {
        updateRecord.accept(record);
        if (record.get(externalIdField) == null) {
            record.set(externalIdField, UUID.randomUUID());
        }
        record.store();
        return record;
    }
}
