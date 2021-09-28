package binarek.robio.auth.user.model;

import binarek.robio.auth.user.view.UserView;
import binarek.robio.util.codegen.BaseStyle;
import org.immutables.value.Value;

import static binarek.robio.auth.user.model.SpecialUsernames.DEFAULT_ADMIN_USERNAME;

@Value.Immutable
@BaseStyle
public abstract class SpecialUser implements UserView {

    SpecialUser() {
    }

    @Override
    public abstract Username getUsername();

    @Override
    public abstract HashedPassword getHashedPassword();

    @Override
    public abstract UserRole getRole();

    public static SpecialUser defaultAdmin(HashedPassword hashedPassword) {
        return ImmutableSpecialUser.builder()
                .username(Username.of(DEFAULT_ADMIN_USERNAME))
                .hashedPassword(hashedPassword)
                .role(UserRole.ADMIN)
                .build();
    }
}
