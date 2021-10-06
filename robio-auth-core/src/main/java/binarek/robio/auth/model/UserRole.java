package binarek.robio.auth.model;

public enum UserRole {
    ADMIN,
    COMPETITOR,
    ORGANIZER,
    ;

    private static final String SPRING_ROLE_PREFIX = "ROLE_";

    public static boolean isValidRole(String role) {
        for (var userRole : values()) {
            if (userRole.name().equals(role)) {
                return true;
            }
        }
        return false;
    }

    public String getSpringRole() {
        return SPRING_ROLE_PREFIX + name();
    }
}
