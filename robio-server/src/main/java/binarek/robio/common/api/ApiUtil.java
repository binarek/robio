package binarek.robio.common.api;

import binarek.robio.common.domain.entity.EntityInvalidIdentityException;
import org.springframework.lang.Nullable;

public final class ApiUtil {

    public static final String DEFAULT_LIMIT = "20";
    public static final String DEFAULT_OFFSET = "0";
    public static final String DEFAULT_DETAILS_LEVEL = "STANDARD";

    public static <ID> void validateEntityPutRequest(ID requestId, @Nullable ID entityId) {
        if (entityId != null && !entityId.equals(requestId)) {
            throw new EntityInvalidIdentityException(entityId);
        }
    }

    private ApiUtil() {
    }
}
