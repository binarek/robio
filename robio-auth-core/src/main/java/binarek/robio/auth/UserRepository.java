package binarek.robio.auth;

import binarek.robio.auth.model.User;
import binarek.robio.auth.model.UserRole;
import binarek.robio.auth.model.Username;

import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> getByUsername(Username username);

    Optional<UserRole> getRoleByUsername(Username username);
}
