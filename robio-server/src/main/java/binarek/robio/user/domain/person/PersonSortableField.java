package binarek.robio.user.domain.person;

public enum PersonSortableField {
    FIRST_NAME,
    LAST_NAME,
    ROLE,
    ;

    public static PersonSortableField fromFieldName(String fieldName) {
        var enumName = fieldName.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
        return PersonSortableField.valueOf(enumName);
    }
}
