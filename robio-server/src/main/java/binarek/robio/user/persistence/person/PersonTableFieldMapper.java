package binarek.robio.user.persistence.person;

import binarek.robio.db.tables.records.PersonRecord;
import binarek.robio.user.domain.person.PersonSortableField;
import org.jooq.TableField;
import org.springframework.stereotype.Component;

import static binarek.robio.db.tables.Person.PERSON;

@Component
public class PersonTableFieldMapper {

    public TableField<PersonRecord, ?> toField(PersonSortableField personSortableField) {
        switch (personSortableField) {
            case FIRST_NAME:
                return PERSON.FIRST_NAME;
            case LAST_NAME:
                return PERSON.LAST_NAME;
            case ROLE:
                return PERSON.ROLE;
            default:
                throw new IllegalArgumentException("Field of type " + personSortableField + " is not supported");
        }
    }
}
