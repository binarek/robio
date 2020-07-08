package binarek.robio.common.util;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class MapperUtil {

    @Nullable
    public static <I, O> O mapNullSafe(@Nullable I input, Function<I, O> mapping) {
        return input == null ? null : mapping.apply(input);
    }

    public static <I, O> List<? extends O> mapNullSafe(@Nullable List<? extends I> input,
                                                       Function<I, O> mapping) {
        if (CollectionUtils.isEmpty(input)) {
            return List.of();
        }
        return input.stream()
                .map(inputElem -> mapNullSafe(inputElem, mapping))
                .collect(Collectors.toList());
    }

    private MapperUtil() {
    }
}
