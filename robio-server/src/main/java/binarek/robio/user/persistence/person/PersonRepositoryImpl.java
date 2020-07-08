package binarek.robio.user.persistence.person;

import binarek.robio.common.persistence.EntityTableHelper;
import binarek.robio.db.tables.records.PersonRecord;
import binarek.robio.user.domain.person.*;
import org.jooq.DSLContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static binarek.robio.common.persistence.EntityPersistenceUtil.*;
import static binarek.robio.db.tables.Person.PERSON;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final DSLContext dsl;
    private final PersonTableMapper personTableMapper;
    private final PersonValueMapper personValueMapper;
    private final EntityTableHelper<PersonRecord> personTableHelper;

    public PersonRepositoryImpl(DSLContext dsl, PersonTableMapper personTableMapper,
                                PersonValueMapper personValueMapper) {
        this.dsl = dsl;
        this.personTableMapper = personTableMapper;
        this.personValueMapper = personValueMapper;
        this.personTableHelper = new EntityTableHelper<>(Person.class, dsl, PERSON, PERSON.EMAIL);
    }

    @Override
    public Optional<? extends Person> getById(PersonId id, @Nullable PersonFetchProperties fetchProperties) {
        return dsl.selectFrom(PERSON).where(PERSON.EXTERNAL_ID.eq(id.getValue())).fetchOptional()
                .map(personTableMapper::toPerson);
    }

    @Override
    public List<? extends Person> getAll(@Nullable PersonFetchProperties fetchProperties) {
        return getPersonRecords(fetchProperties).stream()
                .map(personTableMapper::toPerson)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean existsByName(Email name) {
        return personTableHelper.existsByName(name.getValue());
    }

    @Override
    public boolean existsByIdOrName(@Nullable PersonId id, Email name) {
        return personTableHelper.existsByExternalIdOrName(getValueNullSafe(id), name.getValue());
    }

    @Override
    public Person insert(Person person) {
        var personRecord = personTableHelper.insert(record -> personTableMapper.updateRecord(record, person));
        return personTableMapper.toPerson(personRecord);
    }

    @Override
    public Person insertOrUpdate(Person person) {
        var personRecord = personTableHelper.insertOrUpdate(
                getValueNullSafe(person.getId()), record -> personTableMapper.updateRecord(record, person));
        return personTableMapper.toPerson(personRecord);
    }

    @Override
    public boolean deleteById(PersonId id) {
        return personTableHelper.deleteByExternalId(id.getValue());
    }

    @Override
    public boolean existsByIdAndRole(PersonId id, Person.Role role) {
        return personTableHelper.existsByCondition(PERSON.EXTERNAL_ID.eq(id.getValue())
                .and(PERSON.ROLE.eq(personValueMapper.toValue(role))));
    }

    private List<PersonRecord> getPersonRecords(@Nullable PersonFetchProperties fetchProperties) {
        return personTableHelper.getAll(
                getLimit(fetchProperties),
                getOffset(fetchProperties),
                getSort(fetchProperties, personTableMapper::toField));
    }

    @Nullable
    private static UUID getValueNullSafe(@Nullable PersonId personId) {
        return personId != null ? personId.getValue() : null;
    }
}
