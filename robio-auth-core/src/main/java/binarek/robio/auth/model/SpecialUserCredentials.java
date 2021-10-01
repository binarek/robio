package binarek.robio.auth.model;

import binarek.robio.util.codegen.BaseStyle;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
@BaseStyle
public abstract class SpecialUserCredentials {

    SpecialUserCredentials() {
    }

    public abstract SpecialUsername getUsername();

    public abstract HashedPassword getHashedPassword();

    @Nullable
    public abstract Long getVersion();

    public static SpecialUserCredentials defaultCredentials(SpecialUsername username) {
        return ImmutableSpecialUserCredentials.builder()
                .username(username)
                .hashedPassword(defaultHashedPassword(username))
                .build();
    }

    private static HashedPassword defaultHashedPassword(SpecialUsername username) {
        if (username == SpecialUsername.DEFAULT_ADMIN) {
            // TODO better (more secure) default password definition implementation
            // default password: C0mp3tit1onTim3!
            return HashedPassword.of("$2a$10$V0hKbUlM62SP/fabGByUW.L5JqmbLvChteugLhZjXSaF1JgLHxlQm");
        } else {
            throw new IllegalArgumentException("No default password is defined for username " + username);
        }
    }
}
