package binarek.robio.user.api.person;

import binarek.robio.user.domain.person.Person;
import binarek.robio.user.domain.person.PersonFetchProperties;
import binarek.robio.user.domain.person.PersonService;
import binarek.robio.user.domain.person.PersonSortableField;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static binarek.robio.common.api.ApiUtil.*;

@RestController
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<? extends Person> getPeople(@RequestParam(defaultValue = DEFAULT_LIMIT) int limit,
                                            @RequestParam(defaultValue = DEFAULT_OFFSET) int offset,
                                            @RequestParam(defaultValue = "role,lastName") List<String> sort) {
        return personService.getPeople(PersonFetchProperties.builder()
                .limit(limit)
                .offset(offset)
                .sort(sort.stream().map(PersonSortableField::fromFieldName).collect(Collectors.toUnmodifiableList()))
                .build());
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable UUID id) {
        return personService.getPerson(id);
    }

    @PostMapping
    public Person postPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @PutMapping("/{id}")
    public Person putPerson(@PathVariable UUID id, @RequestBody Person person) {
        validateEntityPutRequest(id, person);
        return personService.savePerson(person);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable UUID id) {
        personService.deletePerson(id);
    }
}
