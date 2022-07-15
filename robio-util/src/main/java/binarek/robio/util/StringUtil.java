package binarek.robio.util;

public final class StringUtil {

    private StringUtil() {
    }

    public static boolean isTrimmed(String string) {
        return string.length() == 0 ||
                (!Character.isWhitespace(string.charAt(0)) && !Character.isWhitespace(string.charAt(string.length() - 1)));
    }
}
