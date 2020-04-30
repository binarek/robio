package binarek.robio.common.api;

import binarek.robio.common.domain.DomainEntity;
import binarek.robio.common.domain.DomainEntityInvalidIdentityException;

import java.util.UUID;

public final class ApiUtil {

    public static final String DEFAULT_DETAILS_LEVEL = "STANDARD";

    public static void validateDomainEntityPutRequest(UUID id, DomainEntity entity) {
        if (entity.getId() != null && !entity.getId().equals(id)) {
            throw new DomainEntityInvalidIdentityException(id);
        }
    }

    private ApiUtil() {
    }
}
