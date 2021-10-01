package binarek.robio.auth.model;

import binarek.robio.shared.model.CompetitorId;
import binarek.robio.util.codegen.BaseStyle;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
@BaseStyle
public abstract class HumanUser implements User {

    HumanUser() {
    }

    public abstract UserId getUserId();

    @Override
    @Value.Derived
    public HumanUsername getUsername() {
        return HumanUsername.of(getEmail().getValue());
    }

    public abstract Email getEmail();

    @Nullable
    public abstract Long getVersion();

    @Value.Redacted
    public abstract HashedPassword getHashedPassword();

    @Override
    public abstract UserRole getRole();

    @Value.Redacted
    public abstract FirstName getFirstName();

    @Value.Redacted
    public abstract LastName getLastName();

    @Nullable
    public abstract CompetitorId getCompetitorId();

    public static HumanUser newAdmin(UserId userId, Email email, HashedPassword hashedPassword,
                                     FirstName firstName, LastName lastName) {
        return ImmutableHumanUser.builder()
                .userId(userId)
                .email(email)
                .hashedPassword(hashedPassword)
                .role(UserRole.ADMIN)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }

    public static HumanUser newCompetitor(UserId userId, Email email, HashedPassword hashedPassword,
                                          FirstName firstName, LastName lastName, CompetitorId competitorId) {
        return ImmutableHumanUser.builder()
                .userId(userId)
                .email(email)
                .hashedPassword(hashedPassword)
                .role(UserRole.COMPETITOR)
                .firstName(firstName)
                .lastName(lastName)
                .competitorId(competitorId)
                .build();
    }

    public static HumanUser newOrganizer(UserId userId, Email email, HashedPassword hashedPassword,
                                         FirstName firstName, LastName lastName) {
        return ImmutableHumanUser.builder()
                .userId(userId)
                .email(email)
                .hashedPassword(hashedPassword)
                .role(UserRole.ORGANIZER)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}
