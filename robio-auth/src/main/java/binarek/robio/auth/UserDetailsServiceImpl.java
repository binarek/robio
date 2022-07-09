package binarek.robio.auth;

import binarek.robio.auth.model.User;
import binarek.robio.auth.model.Username;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserDetailsImpl.of(getUser(username));
    }

    private User getUser(String username) {
        if (Username.isValidUsername(username)) {
            return userRepository.getByUsername(Username.of(username))
                    .orElseThrow(() -> buildUsernameException(username));
        } else {
            throw buildUsernameException(username);
        }
    }

    private static UsernameNotFoundException buildUsernameException(String username) {
        return new UsernameNotFoundException("Cannot find user with username " + username);
    }
}
