package binarek.robio.auth.adapter.persistence;

import binarek.robio.auth.SpecialUserCredentialsRepository;
import binarek.robio.auth.model.SpecialUserCredentials;
import binarek.robio.auth.model.SpecialUsername;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SpecialUserCredentialsRepositoryImpl implements SpecialUserCredentialsRepository {

    @Nullable
    private SpecialUserCredentials adminCredentials;

    @Override
    public void saveIfNotExist(SpecialUserCredentials specialUserCredentials) {
        // TODO implementation
        adminCredentials = specialUserCredentials;
    }

    @Override
    public Optional<SpecialUserCredentials> getByUsername(SpecialUsername username) {
        // TODO implementation
        if (adminCredentials != null) {
            return Optional.of(adminCredentials);
        } else {
            return Optional.empty();
        }
    }
}
