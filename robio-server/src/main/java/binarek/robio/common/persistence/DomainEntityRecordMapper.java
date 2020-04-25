package binarek.robio.common.persistence;

import binarek.robio.common.domain.DomainEntity;
import org.jooq.Record;

public interface DomainEntityRecordMapper<E extends DomainEntity, R extends Record> {

    void updateRecord(R record, E entity);

    E toEntity(R record);
}
