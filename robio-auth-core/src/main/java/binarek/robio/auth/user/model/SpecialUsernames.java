package binarek.robio.auth.user.model;

import java.util.Set;

public final class SpecialUsernames {

    private SpecialUsernames() {
    }

    public static final String DEFAULT_ADMIN_USERNAME = "admin";

    public static Set<String> allUsernames() {
        return Set.of(DEFAULT_ADMIN_USERNAME);
    }
}
