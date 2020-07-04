package binarek.robio.user.persistence.person;

import binarek.robio.common.persistence.EntityFetchProperties;
import binarek.robio.common.persistence.EntityTableHelper;
import binarek.robio.db.tables.records.PersonRecord;
import binarek.robio.user.domain.person.Person;
import binarek.robio.user.domain.person.PersonRepository;
import binarek.robio.user.domain.person.PersonValueMapper;
import org.jooq.DSLContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static binarek.robio.db.tables.Person.PERSON;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final DSLContext dsl;
    private final PersonRecordMapper personRecordMapper;
    private final PersonValueMapper personValueMapper;
    private final EntityTableHelper<PersonRecord> personTableHelper;

    public PersonRepositoryImpl(DSLContext dsl, PersonRecordMapper personRecordMapper,
                                PersonValueMapper personValueMapper) {
        this.dsl = dsl;
        this.personRecordMapper = personRecordMapper;
        this.personValueMapper = personValueMapper;
        this.personTableHelper = new EntityTableHelper<>(Person.class, dsl, PERSON, PERSON.EMAIL);
    }

    @Override
    public Optional<? extends Person> getById(UUID id, @Nullable EntityFetchProperties.NotSupported fetchProperties) {
        return dsl.selectFrom(PERSON).where(PERSON.EXTERNAL_ID.eq(id)).fetchOptional()
                .map(personRecordMapper::toPerson);
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
                person.getId(), record -> personRecordMapper.updateRecord(record, person));
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
}
