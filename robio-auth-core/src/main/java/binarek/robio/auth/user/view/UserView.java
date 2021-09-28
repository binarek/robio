package binarek.robio.auth.user.view;

import binarek.robio.auth.user.model.HashedPassword;
import binarek.robio.auth.user.model.UserRole;
import binarek.robio.auth.user.model.Username;

public interface UserView {

    Username getUsername();

    HashedPassword getHashedPassword();

    UserRole getRole();
}
