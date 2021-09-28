package binarek.robio.auth.user;

import binarek.robio.auth.user.model.Email;
import binarek.robio.auth.user.model.HumanUser;
import binarek.robio.auth.user.model.SpecialUser;
import binarek.robio.auth.user.model.SpecialUsernames;
import binarek.robio.auth.user.view.UserView;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static binarek.robio.auth.user.model.SpecialUsernames.DEFAULT_ADMIN_USERNAME;

@Service
class UserDetailsServiceImpl implements UserDetailsService {

    private final HumanUserRepository humanUserRepository;
    private final SpecialUserPasswordRepository specialUserPasswordRepository;

    UserDetailsServiceImpl(HumanUserRepository humanUserRepository,
                           SpecialUserPasswordRepository specialUserPasswordRepository) {
        this.humanUserRepository = humanUserRepository;
        this.specialUserPasswordRepository = specialUserPasswordRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserDetailsImpl.of(getUser(username));
    }

    private UserView getUser(String username) {
        if (isSpecialUsername(username)) {
            return getSpecialUser(username);
        } else if (isHumanUsername(username)) {
            return getHumanUser(username);
        } else {
            throw buildUsernameException(username);
        }
    }

    private static boolean isSpecialUsername(String username) {
        return SpecialUsernames.allUsernames().contains(username);
    }

    private SpecialUser getSpecialUser(String username) {
        if (DEFAULT_ADMIN_USERNAME.equals(username)) {
            return SpecialUser.defaultAdmin(specialUserPasswordRepository.getDefaultAdminPassword());
        } else {
            throw new IllegalStateException("Special user with username " + username + " is not defined");
        }
    }

    private static boolean isHumanUsername(String username) {
        return EmailValidator.getInstance().isValid(username);
    }

    private HumanUser getHumanUser(String username) {
        return humanUserRepository.getUserByEmail(Email.of(username))
                .orElseThrow(() -> buildUsernameException(username));
    }

    private static UsernameNotFoundException buildUsernameException(String username) {
        return new UsernameNotFoundException("Cannot find user with username " + username);
    }
}
