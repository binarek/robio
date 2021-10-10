package binarek.robio.util;

public final class StringUtil {

    private StringUtil() {
    }

    public static boolean isTrimmed(String string) {
        return string.length() == string.trim().length();
    }
}
