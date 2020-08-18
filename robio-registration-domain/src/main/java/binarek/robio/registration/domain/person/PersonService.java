package binarek.robio.registration.domain.person;

import binarek.robio.registration.domain.common.entity.EntityServiceHelper;
import binarek.robio.registration.domain.common.entity.EntityValueExtractor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final EntityServiceHelper<Person, PersonFetchProperties, PersonId, Email> serviceHelper;

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

    public List<? extends Person> getPeople(PersonFetchProperties fetchProperties) {
        return serviceHelper.getEntities(fetchProperties);
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
