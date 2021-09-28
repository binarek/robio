package binarek.robio.auth.user.model;

import binarek.robio.auth.user.view.UserView;
import binarek.robio.shared.model.CompetitorId;
import binarek.robio.util.codegen.BaseStyle;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
@BaseStyle
public abstract class HumanUser implements UserView {

    HumanUser() {
    }

    public abstract UserId getUserId();

    @Override
    @Value.Derived
    public Username getUsername() {
        return Username.of(getEmail().getValue());
    }

    public abstract Email getEmail();

    @Nullable
    public abstract Long getVersion();

    @Value.Redacted
    public abstract HashedPassword getHashedPassword();

    @Override
    public abstract UserRole getRole();

    @Value.Redacted
    public abstract PersonFirstName getFirstName();

    @Value.Redacted
    public abstract PersonLastName getLastName();

    @Nullable
    public abstract CompetitorId getCompetitorId();

    public static HumanUser newAdmin(UserId userId, Email email, HashedPassword hashedPassword,
                                     PersonFirstName firstName, PersonLastName lastName) {
        return ImmutableHumanUser.builder()
                .userId(userId)
                .email(email)
                .hashedPassword(hashedPassword)
                .firstName(firstName)
                .lastName(lastName)
                .role(UserRole.ADMIN)
                .build();
    }

    public static HumanUser newCompetitor(UserId userId, Email email, HashedPassword hashedPassword,
                                          PersonFirstName firstName, PersonLastName lastName, CompetitorId competitorId) {
        return ImmutableHumanUser.builder()
                .userId(userId)
                .email(email)
                .competitorId(competitorId)
                .hashedPassword(hashedPassword)
                .firstName(firstName)
                .lastName(lastName)
                .role(UserRole.COMPETITOR)
                .build();
    }

    public static HumanUser newOrganizer(UserId userId, Email email, HashedPassword hashedPassword,
                                         PersonFirstName firstName, PersonLastName lastName) {
        return ImmutableHumanUser.builder()
                .userId(userId)
                .email(email)
                .hashedPassword(hashedPassword)
                .firstName(firstName)
                .lastName(lastName)
                .role(UserRole.ORGANIZER)
                .build();
    }
}
