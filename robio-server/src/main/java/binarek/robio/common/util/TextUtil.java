package binarek.robio.common.util;

public final class TextUtil {

    public static String camelToSnakeUpperCase(String camelCaseString) {
        return camelCaseString.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
    }

    private TextUtil() {
    }
}
