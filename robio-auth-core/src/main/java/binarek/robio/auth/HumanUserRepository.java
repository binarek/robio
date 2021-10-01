package binarek.robio.auth;

import binarek.robio.auth.model.HumanUser;
import binarek.robio.auth.model.HumanUsername;

import java.util.Optional;

public interface HumanUserRepository {

    Optional<HumanUser> getByUsername(HumanUsername username);
}
