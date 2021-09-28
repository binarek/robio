package binarek.robio.auth.user;

import binarek.robio.auth.user.model.Email;
import binarek.robio.auth.user.model.HumanUser;

import java.util.Optional;

public interface HumanUserRepository {

    Optional<HumanUser> getUserByEmail(Email email);
}
