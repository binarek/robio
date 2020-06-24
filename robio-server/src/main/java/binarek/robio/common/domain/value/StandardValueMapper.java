package binarek.robio.common.domain.value;

import binarek.robio.core.domain.team.FirstName;
import binarek.robio.core.domain.team.LastName;
import org.springframework.stereotype.Component;

/**
 * Mapper that is responsible for mapping value objects to primitives and vice versa
 */
@Component
public class StandardValueMapper {

    public Notes toNotes(String notes) {
        return Notes.of(notes);
    }

    public String toValue(Notes notes) {
        return notes.getValue();
    }

    public FirstName toPersonFirstName(String firstName) {
        return FirstName.of(firstName);
    }

    public String toValue(FirstName firstName) {
        return firstName.getValue();
    }

    public LastName toPersonLastName(String lastName) {
        return LastName.of(lastName);
    }

    public String toValue(LastName lastName) {
        return lastName.getValue();
    }
}
