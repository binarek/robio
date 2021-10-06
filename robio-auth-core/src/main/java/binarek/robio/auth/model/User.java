package binarek.robio.auth.model;

import binarek.robio.shared.model.CompetitorId;
import binarek.robio.util.codegen.BaseStyle;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

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

    @Value.Check
    protected void validate() {
        if (Username.DEFAULT_ADMIN_USERNAME.equals(getUsername())) {
            Assert.state(getRole() == UserRole.ADMIN, "Default admin must have ADMIN role");
        }
    }

    public static User newDefaultAdminUser(UserId userId, HashedPassword hashedPassword) {
        return ImmutableUser.builder()
                .userId(userId)
                .username(Username.DEFAULT_ADMIN_USERNAME)
                .hashedPassword(hashedPassword)
                .role(UserRole.ADMIN)
                .build();
    }

    public static User newAdmin(UserId userId, Email email, HashedPassword hashedPassword,
                                FirstName firstName, LastName lastName) {
        return ImmutableUser.builder()
                .userId(userId)
                .username(Username.of(email.getValue()))
                .email(email)
                .hashedPassword(hashedPassword)
                .role(UserRole.ADMIN)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }

    public static User newCompetitor(UserId userId, Email email, HashedPassword hashedPassword,
                                     FirstName firstName, LastName lastName, CompetitorId competitorId) {
        return ImmutableUser.builder()
                .userId(userId)
                .username(Username.of(email.getValue()))
                .email(email)
                .hashedPassword(hashedPassword)
                .role(UserRole.COMPETITOR)
                .firstName(firstName)
                .lastName(lastName)
                .competitorId(competitorId)
                .build();
    }

    public static User newOrganizer(UserId userId, Email email, HashedPassword hashedPassword,
                                    FirstName firstName, LastName lastName) {
        return ImmutableUser.builder()
                .userId(userId)
                .username(Username.of(email.getValue()))
                .email(email)
                .hashedPassword(hashedPassword)
                .role(UserRole.ORGANIZER)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}
