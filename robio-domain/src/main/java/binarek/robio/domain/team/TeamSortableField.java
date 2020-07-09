package binarek.robio.domain.team;

import static binarek.robio.util.TextUtil.camelToSnakeUpperCase;

public enum TeamSortableField {
    NAME,
    ;

    public static TeamSortableField fromFieldName(String fieldName) {
        return TeamSortableField.valueOf(camelToSnakeUpperCase(fieldName));
    }
}
