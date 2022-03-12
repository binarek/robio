package binarek.robio.shared;

public final class SecurityExpressions {

    public static final String IS_ADMIN = "hasRole('ROLE_ADMIN')";
    public static final String IS_ADMIN_OR_ORGANIZER = "hasRole('ROLE_ADMIN') or hasRole('ROLE_ORGANIZER')";

    private SecurityExpressions() {
    }
}
