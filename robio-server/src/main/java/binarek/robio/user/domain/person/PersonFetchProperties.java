package binarek.robio.user.domain.person;

import binarek.robio.common.domain.entity.EntityFetchProperties;
import binarek.robio.common.domain.value.SortOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
@JsonDeserialize(as = ImmutablePersonFetchProperties.class)
public abstract class PersonFetchProperties extends EntityFetchProperties<PersonSortableField> {

    PersonFetchProperties() {
    }

    public static Builder builder() {
        return ImmutablePersonFetchProperties.builder();
    }

    public interface Builder {

        Builder limit(@Nullable Integer limit);

        Builder offset(@Nullable Integer offset);

        Builder sort(@Nullable Iterable<? extends SortOrder<PersonSortableField>> sort);

        PersonFetchProperties build();
    }
}
