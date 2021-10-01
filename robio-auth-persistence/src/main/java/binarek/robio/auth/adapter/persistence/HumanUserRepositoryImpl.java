package binarek.robio.auth.adapter.persistence;

import binarek.robio.auth.HumanUserRepository;
import binarek.robio.auth.model.HumanUser;
import binarek.robio.auth.model.HumanUsername;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class HumanUserRepositoryImpl implements HumanUserRepository {

    @Override
    public Optional<HumanUser> getUserByUsername(HumanUsername username) {
        // TODO implementation
        return Optional.empty();
    }
}
