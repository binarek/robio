package binarek.robio.ftl.adapter.persistence;

import binarek.robio.shared.exception.EntityHasChangedException;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.UpdatableRecord;
import org.jooq.exception.DataChangedException;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

class AggregateRootEntityTableManager<R extends UpdatableRecord<R>> {

    private static final Consumer<Long> NO_DEPENDENT_RECORDS_CONSUMER = recordId -> {};

    private final DSLContext dsl;
    private final Table<R> table;

    private final Field<Long> recordIdField;
    private final Field<UUID> businessIdField;

    AggregateRootEntityTableManager(DSLContext dsl, Table<R> table, Field<Long> recordIdField, Field<UUID> businessIdField) {
        this.dsl = dsl;
        this.table = table;
        this.recordIdField = recordIdField;
        this.businessIdField = businessIdField;
    }

    void insertOrUpdate(UUID businessId, Consumer<R> setRecordValues) {
        insertOrUpdate(businessId, setRecordValues, NO_DEPENDENT_RECORDS_CONSUMER, NO_DEPENDENT_RECORDS_CONSUMER);
    }

    void insertOrUpdate(UUID businessId, Consumer<R> setRecordValues,
                        Consumer<Long> deleteDependentRecords, Consumer<Long> insertDependentRecords) {
        dsl.fetchOptional(table, businessIdField.eq(businessId))
                .ifPresentOrElse(
                        record -> update(record, setRecordValues, deleteDependentRecords, insertDependentRecords),
                        () -> insert(setRecordValues, insertDependentRecords));
    }

    boolean existsByBusinessId(UUID businessId) {
        return dsl.fetchExists(table, businessIdField.eq(businessId));
    }

    Optional<R> getByBusinessId(UUID businessId) {
        return dsl.fetchOptional(table, businessIdField.eq(businessId));
    }

    private void insert(Consumer<R> setRecordValues, Consumer<Long> insertDependentRecords) {
        final var record = dsl.newRecord(table);
        store(record, setRecordValues);
        insertDependentRecords.accept(record.get(recordIdField));
    }

    private void update(R record, Consumer<R> setRecordValues,
                        Consumer<Long> deleteDependentRecords, Consumer<Long> insertDependentRecords) {
        store(record, setRecordValues);
        final var databaseId = record.get(recordIdField);
        deleteDependentRecords.accept(databaseId);
        insertDependentRecords.accept(databaseId);
    }

    private void store(R record, Consumer<R> updateRecord) {
        updateRecord.accept(record);
        try {
            record.store();
        } catch (DataChangedException e) {
            throw new EntityHasChangedException(e);
        }
    }
}
