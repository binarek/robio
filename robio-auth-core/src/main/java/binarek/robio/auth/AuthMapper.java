package binarek.robio.auth;

import binarek.robio.auth.model.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static binarek.robio.util.MapperUtil.mapNullSafe;

@Component
public class AuthMapper {

    @Nullable
    public Email toEmail(@Nullable String value) {
        return mapNullSafe(value, Email::of);
    }

    @Nullable
    public String toValue(@Nullable Email email) {
        return mapNullSafe(email, Email::getValue);
    }

    @Nullable
    public FirstName toFirstName(@Nullable String value) {
        return mapNullSafe(value, FirstName::of);
    }

    @Nullable
    public String toValue(@Nullable FirstName name) {
        return mapNullSafe(name, FirstName::getValue);
    }

    @Nullable
    public LastName toLastName(@Nullable String value) {
        return mapNullSafe(value, LastName::of);
    }

    @Nullable
    public String toValue(@Nullable LastName name) {
        return mapNullSafe(name, LastName::getValue);
    }

    @Nullable
    public HumanUsername toHumanUsername(@Nullable String value) {
        return mapNullSafe(value, HumanUsername::of);
    }

    @Nullable
    public String toValue(@Nullable HumanUsername username) {
        return mapNullSafe(username, HumanUsername::getValue);
    }

    @Nullable
    public SpecialUsername toSpecialUsername(@Nullable String value) {
        return mapNullSafe(value, SpecialUsername::of);
    }

    @Nullable
    public String toValue(@Nullable SpecialUsername username) {
        return mapNullSafe(username, SpecialUsername::getValue);
    }

    @Nullable
    public HashedPassword toHashedPassword(@Nullable String value) {
        return mapNullSafe(value, HashedPassword::of);
    }

    @Nullable
    public String toValue(@Nullable HashedPassword password) {
        return mapNullSafe(password, HashedPassword::getValue);
    }

    @Nullable
    public UserId toUserId(@Nullable UUID value) {
        return mapNullSafe(value, UserId::of);
    }

    @Nullable
    public UUID toValue(@Nullable UserId id) {
        return mapNullSafe(id, UserId::getValue);
    }
}
