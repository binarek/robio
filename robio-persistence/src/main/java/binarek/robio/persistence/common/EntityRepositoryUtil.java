package binarek.robio.persistence.common;

import binarek.robio.domain.common.entity.EntityFetchProperties;
import binarek.robio.domain.common.value.SortOrder;
import org.jooq.SortField;
import org.jooq.TableField;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.function.Function;

import static binarek.robio.util.MapperUtil.mapListNullSafe;

public final class EntityRepositoryUtil {

    @Nullable
    public static Integer getLimit(@Nullable EntityFetchProperties<?> fetchProperties) {
        return fetchProperties != null ? fetchProperties.getLimit() : null;
    }

    @Nullable
    public static Integer getOffset(@Nullable EntityFetchProperties<?> fetchProperties) {
        return fetchProperties != null ? fetchProperties.getOffset() : null;
    }

    @SuppressWarnings("unchecked")
    public static <SF> List<SortField<?>> getSort(@Nullable EntityFetchProperties<SF> fetchProperties,
                                                  Function<SF, TableField<?, ?>> toField) {
        if (fetchProperties != null) {
            return (List<SortField<?>>) mapListNullSafe(fetchProperties.getSort(), order -> toSortField(order, toField));
        } else {
            return List.of();
        }
    }

    private static <SF> SortField<?> toSortField(SortOrder<SF> order,
                                                 Function<SF, TableField<?, ?>> toField) {
        var tableField = toField.apply(order.getProperty());
        return order.getDirection() == SortOrder.Direction.ASC ? tableField.asc() : tableField.desc();
    }

    private EntityRepositoryUtil() {
    }
}
