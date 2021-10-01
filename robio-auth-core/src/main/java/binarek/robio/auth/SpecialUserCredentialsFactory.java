package binarek.robio.auth;

import binarek.robio.auth.model.HashedPassword;
import binarek.robio.auth.model.SpecialUserCredentials;
import binarek.robio.auth.model.SpecialUsername;
import org.springframework.stereotype.Component;

@Component
public class SpecialUserCredentialsFactory {

    SpecialUserCredentialsFactory() {
    }

    public SpecialUserCredentials defaultCredentials(SpecialUsername username) {
        return SpecialUserCredentials.newCredentials(username, getDefaultPassword(username));
    }

    private HashedPassword getDefaultPassword(SpecialUsername username) {
        if (username == SpecialUsername.DEFAULT_ADMIN) {
            // default password: C0mp3tit1onTim3!
            return HashedPassword.of("$2a$10$V0hKbUlM62SP/fabGByUW.L5JqmbLvChteugLhZjXSaF1JgLHxlQm");
        } else {
            throw new IllegalArgumentException("No default password is defined for username " + username);
        }
    }
}
