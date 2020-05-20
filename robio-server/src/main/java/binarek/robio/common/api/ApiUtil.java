package binarek.robio.common.api;

import binarek.robio.common.domain.entity.Entity;
import binarek.robio.common.domain.entity.EntityInvalidIdentityException;

import java.util.UUID;

public final class ApiUtil {

    public static final String DEFAULT_DETAILS_LEVEL = "STANDARD";

    public static void validateEntityPutRequest(UUID id, Entity entity) {
        if (entity.getId() != null && !entity.getId().equals(id)) {
            throw new EntityInvalidIdentityException(id);
        }
    }

    private ApiUtil() {
    }
}
