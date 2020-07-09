package binarek.robio.domain.person;

import static binarek.robio.util.TextUtil.camelToSnakeUpperCase;

public enum PersonSortableField {
    FIRST_NAME,
    LAST_NAME,
    ROLE,
    ;

    public static PersonSortableField fromFieldName(String fieldName) {
        return PersonSortableField.valueOf(camelToSnakeUpperCase(fieldName));
    }
}
