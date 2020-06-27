package binarek.robio.common.domain.value;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static binarek.robio.common.util.MapperUtil.mapNullSafe;

/**
 * Mapper that is responsible for mapping value objects to primitives and vice versa
 */
@Component
public class StandardValueMapper {

    @Nullable
    public Notes toNotes(@Nullable String notes) {
        return mapNullSafe(notes, Notes::of);
    }

    @Nullable
    public String toValue(@Nullable Notes notes) {
        return mapNullSafe(notes, Notes::getValue);
    }
}
