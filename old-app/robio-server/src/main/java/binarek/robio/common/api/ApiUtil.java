package binarek.robio.common.api;

import binarek.robio.common.domain.entity.EntityFetchProperties;
import binarek.robio.common.domain.value.SortOrder;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class ApiUtil {

    public static final String DEFAULT_LIMIT = "20";
    public static final String DEFAULT_OFFSET = "0";
    public static final String DEFAULT_DETAILS_LEVEL = "STANDARD";

    private static final int MAX_FETCH_LIMIT = 200;

    public static <ID> void validateEntityPutRequest(ID pathEntityId, @Nullable ID bodyEntityId) {
        if (bodyEntityId != null && !bodyEntityId.equals(pathEntityId)) {
            throw new UnprocessableEntityException("Entity has invalid id " + bodyEntityId);
        }
    }

    public static <FP extends EntityFetchProperties<?>> FP buildAndValidateFetchProperties(Supplier<FP> fetchPropertiesBuilder) {
        FP fetchProperties;
        try {
            fetchProperties = fetchPropertiesBuilder.get();
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new BadRequestException(e.getLocalizedMessage());
        }
        validateFetchProperties(fetchProperties);
        return fetchProperties;
    }

    private static void validateFetchProperties(EntityFetchProperties<?> fetchProperties) {
        var limit = fetchProperties.getLimit();
        if (limit != null && limit > MAX_FETCH_LIMIT) {
            throw new BadRequestException("Maximum allowed limit is " + MAX_FETCH_LIMIT);
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
