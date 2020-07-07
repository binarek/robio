package binarek.robio.user.domain.person;

import binarek.robio.common.domain.entity.EntityServiceHelper;
import binarek.robio.common.domain.entity.EntityValueExtractor;
import binarek.robio.common.persistence.EntityFetchProperties;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final EntityServiceHelper<Person, EntityFetchProperties.NotSupported, PersonId, Email> serviceHelper;

    public PersonService(PersonRepository personRepository) {
        this.serviceHelper = new EntityServiceHelper<>(Person.class, personRepository, new PersonValueExtractor());
    }

    public Person createPerson(Person person) {
        return serviceHelper.createEntity(person);
    }

    public Person savePerson(Person person) {
        return serviceHelper.saveEntity(person);
    }

    public void deletePerson(PersonId id) {
        serviceHelper.deleteEntity(id);
    }

    public Person getPerson(PersonId id) {
        return serviceHelper.getEntity(id);
    }

    private static final class PersonValueExtractor implements EntityValueExtractor<Person, PersonId, Email> {
        @Override
        @Nullable
        public PersonId getId(Person person) {
            return person.getId();
        }

        @Override
        public Email getName(Person person) {
            return person.getEmail();
        }
    }
}
