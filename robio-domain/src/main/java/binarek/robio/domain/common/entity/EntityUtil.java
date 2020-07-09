package binarek.robio.domain.common.entity;

final class EntityUtil {

    static String name(Class<? extends Entity> entityClass) {
        return entityClass.getSimpleName();
    }

    private EntityUtil() {
    }
}
