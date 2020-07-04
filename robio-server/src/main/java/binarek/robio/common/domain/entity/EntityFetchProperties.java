package binarek.robio.common.domain.entity;

import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @param <SF> sortable entity field
 */
public interface EntityFetchProperties<SF> {

    @Nullable
    Integer getLimit();

    @Nullable
    Integer getOffset();

    @Nullable
    List<SF> getSort();
}
