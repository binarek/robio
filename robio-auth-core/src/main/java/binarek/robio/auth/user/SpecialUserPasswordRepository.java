package binarek.robio.auth.user;

import binarek.robio.auth.user.model.HashedPassword;

public interface SpecialUserPasswordRepository {

    HashedPassword getDefaultAdminPassword();
}
