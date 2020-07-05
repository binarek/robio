package binarek.robio.common.persistence;

import binarek.robio.common.domain.entity.EntityFetchProperties;
import org.jooq.Record;
import org.jooq.TableField;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.function.Function;

import static binarek.robio.common.util.MapperUtil.mapNullSafe;

public final class EntityPersistenceUtil {

    @Nullable
    public static Integer getLimit(@Nullable EntityFetchProperties<?> fetchProperties) {
        return fetchProperties != null ? fetchProperties.getLimit() : null;
    }

    @Nullable
    public static Integer getOffset(@Nullable EntityFetchProperties<?> fetchProperties) {
        return fetchProperties != null ? fetchProperties.getOffset() : null;
    }

    @SuppressWarnings("unchecked")
    public static <R extends Record, SF> List<TableField<R, ?>> getSort(@Nullable EntityFetchProperties<SF> fetchProperties,
                                                                        Function<SF, TableField<R, ?>> toField) {
        if (fetchProperties != null) {
            return (List<TableField<R, ?>>) mapNullSafe(fetchProperties.getSort(), toField);
        } else {
            return List.of();
        }
    }

    private EntityPersistenceUtil() {
    }
}
