package binarek.robio.auth.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize
@Value.Style(redactedMask = "***", stagedBuilder = true)
public abstract class SpecialUser implements User {

    SpecialUser() {
    }

    @Override
    public abstract SpecialUsername getUsername();

    @Override
    public abstract HashedPassword getHashedPassword();

    @Override
    public abstract UserRole getRole();

    public static SpecialUser newUser(SpecialUserCredentials specialUserCredentials) {
        return ImmutableSpecialUser.builder()
                .username(specialUserCredentials.getUsername())
                .hashedPassword(specialUserCredentials.getHashedPassword())
                .role(resolveRole(specialUserCredentials.getUsername()))
                .build();
    }

    private static UserRole resolveRole(SpecialUsername username) {
        if (username == SpecialUsername.DEFAULT_ADMIN) {
            return UserRole.ADMIN;
        } else {
            throw new IllegalStateException("Cannot resolve role for username " + username);
        }
    }
}
