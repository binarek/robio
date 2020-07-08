package binarek.robio.core.domain.robot;

import static binarek.robio.common.util.TextUtil.camelToSnakeUpperCase;

public enum RobotSortableField {
    HEIGHT,
    LENGTH,
    NAME,
    WEIGHT,
    WIDTH,
    ;

    public static RobotSortableField fromFieldName(String fieldName) {
        return RobotSortableField.valueOf(camelToSnakeUpperCase(fieldName));
    }
}
