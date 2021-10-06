package binarek.robio.auth;

import binarek.robio.auth.model.HashedPassword;
import binarek.robio.auth.model.UserCredentials;
import binarek.robio.auth.model.Username;
import org.springframework.stereotype.Component;

@Component
public class UserCredentialsFactory {

    UserCredentialsFactory() {
    }

    public UserCredentials defaultCredentials(Username username) {
        return UserCredentials.newCredentials(username, getDefaultPassword(username));
    }

    private HashedPassword getDefaultPassword(Username username) {
        if (Username.DEFAULT_ADMIN_USERNAME.equals(username)) {
            // default password: C0mp3tit1onTim3!
            return HashedPassword.of("$2a$10$V0hKbUlM62SP/fabGByUW.L5JqmbLvChteugLhZjXSaF1JgLHxlQm");
        } else {
            throw new IllegalArgumentException("Cannot resolve credentials for username " + username);
        }
    }
}
