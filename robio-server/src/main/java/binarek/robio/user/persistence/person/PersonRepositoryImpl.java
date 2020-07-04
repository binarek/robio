package binarek.robio.user.persistence.person;

import binarek.robio.common.domain.entity.EntityFetchProperties;
import binarek.robio.common.persistence.EntityTableHelper;
import binarek.robio.db.tables.records.PersonRecord;
import binarek.robio.user.domain.person.*;
import org.jooq.DSLContext;
import org.jooq.TableField;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static binarek.robio.common.util.MapperUtil.mapNullSafe;
import static binarek.robio.db.tables.Person.PERSON;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final DSLContext dsl;
    private final PersonRecordMapper personRecordMapper;
    private final PersonTableFieldMapper personTableFieldMapper;
    private final PersonValueMapper personValueMapper;
    private final EntityTableHelper<PersonRecord> personTableHelper;

    public PersonRepositoryImpl(DSLContext dsl,
                                PersonRecordMapper personRecordMapper,
                                PersonTableFieldMapper personTableFieldMapper,
                                PersonValueMapper personValueMapper) {
        this.dsl = dsl;
        this.personRecordMapper = personRecordMapper;
        this.personTableFieldMapper = personTableFieldMapper;
        this.personValueMapper = personValueMapper;
        this.personTableHelper = new EntityTableHelper<>(Person.class, dsl, PERSON, PERSON.EMAIL);
    }

    @Override
    public Optional<? extends Person> getById(UUID id, @Nullable PersonFetchProperties fetchProperties) {
        return dsl.selectFrom(PERSON).where(PERSON.EXTERNAL_ID.eq(id)).fetchOptional()
                .map(personRecordMapper::toPerson);
    }

    @Override
    public List<? extends Person> getAll(@Nullable PersonFetchProperties fetchProperties) {
        return getPersonRecords(fetchProperties).stream()
                .map(personRecordMapper::toPerson)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean existsByName(String name) {
        return personTableHelper.existsByName(name);
    }

    @Override
    public boolean existsByIdOrName(@Nullable UUID id, String name) {
        return personTableHelper.existsByExternalIdOrName(id, name);
    }

    @Override
    public Person insert(Person person) {
        var personRecord = personTableHelper.insert(record -> personRecordMapper.updateRecord(record, person));
        return personRecordMapper.toPerson(personRecord);
    }

    @Override
    public Person insertOrUpdate(Person person) {
        var personRecord = personTableHelper.insertOrUpdate(
                person.getIdValue(), record -> personRecordMapper.updateRecord(record, person));
        return personRecordMapper.toPerson(personRecord);
    }

    @Override
    public boolean deleteById(UUID id) {
        return personTableHelper.deleteByExternalId(id);
    }

    @Override
    public boolean existsByIdAndRole(UUID id, Person.Role role) {
        return personTableHelper.existsByCondition(PERSON.EXTERNAL_ID.eq(id)
                .and(PERSON.ROLE.eq(personValueMapper.toValue(role))));
    }

    @SuppressWarnings("unchecked")
    private List<PersonRecord> getPersonRecords(@Nullable EntityFetchProperties<PersonSortableField> fetchProperties) {
        Integer limit = null;
        Integer offset = null;
        List<TableField<PersonRecord, ?>> orderFields = List.of();

        if (fetchProperties != null) {
            limit = fetchProperties.getLimit();
            offset = fetchProperties.getOffset();
            orderFields = (List<TableField<PersonRecord, ?>>) mapNullSafe(fetchProperties.getSort(), personTableFieldMapper::toField);
        }
        return personTableHelper.getAll(limit, offset, orderFields);
    }
}
