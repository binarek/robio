package binarek.robio.common.domain.value;

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
}
