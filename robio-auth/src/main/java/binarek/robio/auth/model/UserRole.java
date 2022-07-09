package binarek.robio.auth.model;

public enum UserRole {
    ADMIN,
    COMPETITOR,
    ORGANIZER,
    ;

    public static boolean isValidRole(String role) {
        for (var userRole : values()) {
            if (userRole.name().equals(role)) {
                return true;
            }
        }
        return false;
    }
}
