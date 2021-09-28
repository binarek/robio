package binarek.robio.auth.adapter.persistence;

import binarek.robio.auth.user.HumanUserRepository;
import binarek.robio.auth.user.model.Email;
import binarek.robio.auth.user.model.HumanUser;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class HumanUserRepositoryImpl implements HumanUserRepository {

    @Override
    public Optional<HumanUser> getUserByEmail(Email email) {
        // TODO implementation
        return Optional.empty();
    }
}
