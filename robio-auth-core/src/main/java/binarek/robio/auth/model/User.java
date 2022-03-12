package binarek.robio.auth.model;

import binarek.robio.shared.model.CompetitorId;
import binarek.robio.util.codegen.BaseStyle;
import org.apache.commons.lang3.Validate;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
@BaseStyle
public abstract class User {

    User() {
    }

    public abstract UserId getUserId();

    public abstract Username getUsername();

    @Nullable
    public abstract Email getEmail();

    @Nullable
    public abstract Long getVersion();

    @Value.Redacted
    public abstract HashedPassword getHashedPassword();

    public abstract UserRole getRole();

    @Nullable
    @Value.Redacted
    public abstract FirstName getFirstName();

    @Nullable
    @Value.Redacted
    public abstract LastName getLastName();

    @Nullable
    public abstract CompetitorId getCompetitorId();

    public static User newUser(UserId userId, Username username, HashedPassword hashedPassword, UserRole role) {
        return ImmutableUser.builder()
                .userId(userId)
                .username(username)
                .hashedPassword(hashedPassword)
                .role(role)
                .build();
    }
}
