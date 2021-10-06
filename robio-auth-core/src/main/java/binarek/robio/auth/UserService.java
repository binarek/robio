package binarek.robio.auth;

import binarek.robio.auth.model.User;
import binarek.robio.auth.model.UserId;
import binarek.robio.auth.model.Username;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserCredentialsFactory userCredentialsFactory;
    private final UserRepository userRepository;

    UserService(UserCredentialsFactory userCredentialsFactory,
                UserRepository userRepository) {
        this.userCredentialsFactory = userCredentialsFactory;
        this.userRepository = userRepository;
    }

    /**
     * Returns user with given username.
     * Special users always exits.
     * If special user is missing in repository a new one is created, persisted and returned.
     * Users of the other types may or may not exist.
     *
     * @param username username
     * @return user, which always exists if special user and may not exist for the other types of users
     */
    public Optional<User> getUser(Username username) {
        final var userOptional = userRepository.getByUsername(username);
        if (userOptional.isPresent() || !username.isSpecial()) {
            return userOptional;
        } else {
            return createAndGetSpecialUser(username);
        }
    }

    private Optional<User> createAndGetSpecialUser(Username username) {
        createSpecialUser(username);
        final var userOptional = userRepository.getByUsername(username);
        if (userOptional.isEmpty()) {
            throw new IllegalStateException("Cannot get special user for username " + username);
        }
        return userOptional;
    }

    private void createSpecialUser(Username username) {
        final var user = User.newDefaultAdminUser(
                UserId.of(UUID.randomUUID()), userCredentialsFactory.defaultCredentials(username).getHashedPassword());
        userRepository.saveIfNotExistByUsername(user);
    }
}
