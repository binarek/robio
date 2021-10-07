package binarek.robio.auth;

import binarek.robio.auth.model.User;
import binarek.robio.auth.model.UserId;
import binarek.robio.auth.model.Username;

import java.util.Optional;

public interface UserRepository {

    void saveIfNotExistByUsername(User user);

    Optional<User> getByUserId(UserId userId);

    Optional<User> getByUsername(Username username);
}
