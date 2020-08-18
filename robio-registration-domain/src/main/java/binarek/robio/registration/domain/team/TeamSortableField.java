package binarek.robio.registration.domain.team;

import static binarek.robio.common.util.TextUtil.camelToSnakeUpperCase;

public enum TeamSortableField {
    NAME,
    ;

    public static TeamSortableField fromFieldName(String fieldName) {
        return TeamSortableField.valueOf(camelToSnakeUpperCase(fieldName));
    }
}
