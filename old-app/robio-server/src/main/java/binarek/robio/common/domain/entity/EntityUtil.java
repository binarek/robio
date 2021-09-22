package binarek.robio.common.domain.entity;

final class EntityUtil {

    static String name(Class<? extends Entity> entityClass) {
        return entityClass.getSimpleName();
    }

    private EntityUtil() {
    }
}
