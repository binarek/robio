package binarek.robio.user.persistence.person;

import binarek.robio.common.persistence.EntityTableHelper;
import binarek.robio.db.tables.records.PersonRecord;
import binarek.robio.user.domain.person.Person;
import binarek.robio.user.domain.person.PersonFetchProperties;
import binarek.robio.user.domain.person.PersonRepository;
import binarek.robio.user.domain.person.PersonValueMapper;
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

    public PersonRepositoryImpl(DSLContext dsl,
                                PersonTableMapper personTableMapper,
                                PersonValueMapper personValueMapper) {
        this.dsl = dsl;
        this.personTableMapper = personTableMapper;
        this.personValueMapper = personValueMapper;
        this.personTableHelper = new EntityTableHelper<>(Person.class, dsl, PERSON, PERSON.EMAIL);
    }

    @Override
    public Optional<? extends Person> getById(UUID id, @Nullable PersonFetchProperties fetchProperties) {
        return dsl.selectFrom(PERSON).where(PERSON.EXTERNAL_ID.eq(id)).fetchOptional()
                .map(personTableMapper::toPerson);
    }

    @Override
    public List<? extends Person> getAll(@Nullable PersonFetchProperties fetchProperties) {
        return getPersonRecords(fetchProperties).stream()
                .map(personTableMapper::toPerson)
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
        var personRecord = personTableHelper.insert(record -> personTableMapper.updateRecord(record, person));
        return personTableMapper.toPerson(personRecord);
    }

    @Override
    public Person insertOrUpdate(Person person) {
        var personRecord = personTableHelper.insertOrUpdate(
                person.getIdValue(), record -> personTableMapper.updateRecord(record, person));
        return personTableMapper.toPerson(personRecord);
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

    private List<PersonRecord> getPersonRecords(@Nullable PersonFetchProperties fetchProperties) {
        return personTableHelper.getAll(
                getLimit(fetchProperties),
                getOffset(fetchProperties),
                getSort(fetchProperties, personTableMapper::toField));
    }
}
