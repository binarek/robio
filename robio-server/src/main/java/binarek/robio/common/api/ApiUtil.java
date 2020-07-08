package binarek.robio.common.api;

import binarek.robio.common.domain.entity.EntityInvalidIdentityException;
import binarek.robio.common.domain.value.SortOrder;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ApiUtil {

    public static final String DEFAULT_LIMIT = "20";
    public static final String DEFAULT_OFFSET = "0";
    public static final String DEFAULT_DETAILS_LEVEL = "STANDARD";

    public static <ID> void validateEntityPutRequest(ID requestId, @Nullable ID entityId) {
        if (entityId != null && !entityId.equals(requestId)) {
            throw new EntityInvalidIdentityException(entityId);
        }
    }

    public static <SF> List<SortOrder<SF>> toSort(List<String> orders, Function<String, SF> toProperty) {
        return orders.stream()
                .map(order -> toSortOrder(order, toProperty))
                .collect(Collectors.toUnmodifiableList());
    }

    private static <SF> SortOrder<SF> toSortOrder(String order, Function<String, SF> toProperty) {
        switch (order.charAt(0)) {
            case '+':
                return SortOrder.of(toProperty.apply(order.substring(1)), SortOrder.Direction.ASC);
            case '-':
                return SortOrder.of(toProperty.apply(order.substring(1)), SortOrder.Direction.DESC);
            default:
                return SortOrder.of(toProperty.apply(order), SortOrder.Direction.ASC);
        }
    }

    private ApiUtil() {
    }
}
