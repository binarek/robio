package binarek.robio.common.domain.entity;

import org.immutables.value.Value;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @param <SF> sortable entity field
 */
public abstract class EntityFetchProperties<SF> {

    private static final int MAX_LIMIT = 200;

    @Nullable
    public abstract Integer getLimit();

    @Nullable
    public abstract Integer getOffset();

    @Nullable
    public abstract List<SF> getSort();

    @Value.Check
    protected void validate() {
        Assert.state(getLimit() == null || getLimit() > 0 || getLimit() < MAX_LIMIT,
                "Limit has to be positive and lower than " + MAX_LIMIT + " or null");
        Assert.state(getOffset() == null || getOffset() >= 0,
                "Offset has to be positive, zero or null");
    }
}
