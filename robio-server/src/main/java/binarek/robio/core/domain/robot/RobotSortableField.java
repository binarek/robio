package binarek.robio.core.domain.robot;

public enum RobotSortableField {
    HEIGHT,
    LENGTH,
    NAME,
    WEIGHT,
    WIDTH,
    ;

    public static RobotSortableField fromFieldName(String fieldName) {
        return RobotSortableField.valueOf(fieldName.toUpperCase());
    }
}
