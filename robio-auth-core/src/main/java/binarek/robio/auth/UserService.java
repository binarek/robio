package binarek.robio.auth;

import binarek.robio.auth.model.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final SpecialUserCredentialsRepository specialUserCredentialsRepository;
    private final HumanUserRepository humanUserRepository;

    UserService(SpecialUserCredentialsRepository specialUserCredentialsRepository,
                HumanUserRepository humanUserRepository) {
        this.specialUserCredentialsRepository = specialUserCredentialsRepository;
        this.humanUserRepository = humanUserRepository;
    }

    /**
     * Returns user with given username.
     * Two types of users are handled: special users and human users.
     * In case of special user, username needs to be an instance of SpecialUsername. Special user always exits.
     * If special user credentials are missing in repository new ones are created, persisted and returned.
     * In case of human user, username needs to be an instance of HumanUsername. Human user may or may not exist.
     *
     * @param username username, needs to an instance of SpecialUsername or HumanUsername class
     * @return user, which always exists if special user and may not exist if human user
     */
    public Optional<? extends User> getUser(Username username) {
        if (username instanceof SpecialUsername) {
            return Optional.of(getSpecialUser((SpecialUsername) username));
        } else if (username instanceof HumanUsername) {
            return getHumanUser((HumanUsername) username);
        } else {
            throw new IllegalArgumentException("Cannot get user for username class " + username.getClass().getName());
        }
    }

    private Optional<HumanUser> getHumanUser(HumanUsername username) {
        return humanUserRepository.getUserByUsername(username);
    }

    private SpecialUser getSpecialUser(SpecialUsername username) {
        return specialUserCredentialsRepository.getByUsername(username)
                .map(SpecialUser::newUser)
                .orElseGet(() -> createAndGetSpecialUser(username));
    }

    private SpecialUser createAndGetSpecialUser(SpecialUsername username) {
        specialUserCredentialsRepository.saveIfNotExist(SpecialUserCredentials.defaultCredentials(username));
        return specialUserCredentialsRepository.getByUsername(username)
                .map(SpecialUser::newUser)
                .orElseThrow(() -> new IllegalStateException("Cannot get special user for username " + username));
    }
}
