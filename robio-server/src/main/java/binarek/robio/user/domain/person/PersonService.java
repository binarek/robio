package binarek.robio.user.domain.person;

import binarek.robio.common.domain.entity.EntityServiceHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PersonService {

    private final EntityServiceHelper<Person, PersonFetchProperties> serviceHelper;

    public PersonService(PersonRepository personRepository) {
        this.serviceHelper = new EntityServiceHelper<>(Person.class, personRepository);
    }

    public Person createPerson(Person person) {
        return serviceHelper.createEntity(person);
    }

    public Person savePerson(Person person) {
        return serviceHelper.saveEntity(person);
    }

    public void deletePerson(UUID id) {
        serviceHelper.deleteEntity(id);
    }

    public Person getPerson(UUID id) {
        return serviceHelper.getEntity(id);
    }

    public List<? extends Person> getPeople(PersonFetchProperties fetchProperties) {
        return serviceHelper.getEntities(fetchProperties);
    }
}
