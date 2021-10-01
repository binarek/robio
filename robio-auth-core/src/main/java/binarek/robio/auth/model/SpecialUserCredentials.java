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

    public static SpecialUserCredentials newCredentials(SpecialUsername username, HashedPassword password) {
        return ImmutableSpecialUserCredentials.builder()
                .username(username)
                .hashedPassword(password)
                .build();
    }
}
