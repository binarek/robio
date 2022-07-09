package binarek.robio.auth.model;

public final class Authorities {

    public static final String GENERATE_TOKENS = "GENERATE_TOKENS";

    private static final String SPRING_ROLE_PREFIX = "ROLE_";

    public static String getForRole(UserRole userRole) {
        return SPRING_ROLE_PREFIX + userRole.name();
    }

    private Authorities() {
    }
}
