package binarek.robio.util;

import org.springframework.lang.Nullable;

import java.util.function.Function;

public final class MapperUtil {

    @Nullable
    public static <I, O> O mapNullSafe(@Nullable I input, Function<I, O> mapping) {
        return input == null ? null : mapping.apply(input);
    }

    private MapperUtil() {
    }
}
