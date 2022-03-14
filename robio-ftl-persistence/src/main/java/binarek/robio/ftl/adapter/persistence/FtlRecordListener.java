package binarek.robio.ftl.adapter.persistence;

import binarek.robio.shared.RequestContextProvider;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.RecordContext;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultRecordListener;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class FtlRecordListener extends DefaultRecordListener {

    private static final Field<OffsetDateTime> CREATED_AT = DSL.field(DSL.name("CREATED_AT"), OffsetDateTime.class);
    private static final Field<String> CREATED_BY = DSL.field(DSL.name("CREATED_BY"), String.class);
    private static final Field<OffsetDateTime> MODIFIED_AT = DSL.field(DSL.name("MODIFIED_AT"), OffsetDateTime.class);
    private static final Field<String> MODIFIED_BY = DSL.field(DSL.name("MODIFIED_BY"), String.class);

    private final RequestContextProvider requestContextProvider;

    public FtlRecordListener(RequestContextProvider requestContextProvider) {
        this.requestContextProvider = requestContextProvider;
    }

    @Override
    public void insertStart(RecordContext ctx) {
        final var username = requestContextProvider.getContext().getUser().getUsername();
        final var now = OffsetDateTime.now();

        final var record = ctx.record();
        setCreatedAt(record, now);
        setCreatedBy(record, username);
        setModifiedAt(record, now);
        setModifiedBy(record, username);
    }

    @Override
    public void updateStart(RecordContext ctx) {
        final var username = requestContextProvider.getContext().getUser().getUsername();
        final var now = OffsetDateTime.now();

        final var record = ctx.record();
        setModifiedAt(record, now);
        setModifiedBy(record, username);
    }

    private static void setCreatedAt(Record record, OffsetDateTime dateTime) {
        record.set(CREATED_AT, dateTime);
    }

    private static void setCreatedBy(Record record, String username) {
        record.set(CREATED_BY, username);
    }

    private static void setModifiedAt(Record record, OffsetDateTime dateTime) {
        record.set(MODIFIED_AT, dateTime);
    }

    private static void setModifiedBy(Record record, String username) {
        record.set(MODIFIED_BY, username);
    }
}
