package binarek.robio.user.api.person;

import binarek.robio.common.api.BadRequestException;
import binarek.robio.user.domain.person.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
        return personService.getPeople(buildPersonFetchProperties(limit, offset, sort));
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

    private static PersonFetchProperties buildPersonFetchProperties(int limit, int offset, List<String> sort) {
        try {
            return PersonFetchProperties.builder()
                    .limit(limit)
                    .offset(offset)
                    .sort(toSort(sort, PersonSortableField::fromFieldName))
                    .build();
        } catch (IllegalStateException | IllegalArgumentException e) {
            throw new BadRequestException(e.getLocalizedMessage());
        }
    }
}
