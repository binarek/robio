package binarek.robio.user.api.person;

import binarek.robio.user.domain.person.Person;
import binarek.robio.user.domain.person.PersonId;
import binarek.robio.user.domain.person.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static binarek.robio.common.api.ApiUtil.validateEntityPutRequest;

@RestController
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable PersonId id) {
        return personService.getPerson(id);
    }

    @PostMapping
    public Person postPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @PutMapping("/{id}")
    public Person putPerson(@PathVariable UUID id, @RequestBody Person person) {
        validateEntityPutRequest(id, person.getId());
        return personService.savePerson(person);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable PersonId id) {
        personService.deletePerson(id);
    }
}
