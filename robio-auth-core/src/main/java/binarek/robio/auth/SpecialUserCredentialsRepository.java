package binarek.robio.auth;

import binarek.robio.auth.model.SpecialUserCredentials;
import binarek.robio.auth.model.SpecialUsername;

import java.util.Optional;

public interface SpecialUserCredentialsRepository {

    void saveIfNotExist(SpecialUserCredentials specialUserCredentials);

    Optional<SpecialUserCredentials> getByUsername(SpecialUsername username);
}
