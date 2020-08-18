package binarek.robio.registration.domain.common.entity;

import binarek.robio.registration.domain.common.value.SortOrder;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @param <SF> sortable entity field
 */
public abstract class EntityFetchProperties<SF> {

    @Nullable
    public abstract Integer getLimit();

    @Nullable
    public abstract Integer getOffset();

    @Nullable
    public abstract List<SortOrder<SF>> getSort();

    @Value.Check
    protected void validate() {
        Assert.state(getLimit() == null || getLimit() > 0, "Limit has to be positive or null");
        Assert.state(getOffset() == null || getOffset() >= 0, "Offset has to be positive, zero or null");
    }
}
