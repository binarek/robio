package binarek.robio.auth.model;

import binarek.robio.util.codegen.BaseStyle;
import org.immutables.value.Value;

@Value.Immutable
@BaseStyle
public abstract class UserCredentials {

    UserCredentials() {
    }

    public abstract Username getUsername();

    public abstract HashedPassword getHashedPassword();

    public static UserCredentials newCredentials(Username username, HashedPassword password) {
        return ImmutableUserCredentials.builder()
                .username(username)
                .hashedPassword(password)
                .build();
    }
}
