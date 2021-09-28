package binarek.robio.auth.adapter.persistence;

import binarek.robio.auth.user.SpecialUserPasswordRepository;
import binarek.robio.auth.user.model.HashedPassword;
import org.springframework.stereotype.Repository;

@Repository
public class SpecialUserPasswordRepositoryImpl implements SpecialUserPasswordRepository {

    @Override
    public HashedPassword getDefaultAdminPassword() {
        // TODO implementation
        // password: Admin
        return HashedPassword.of("$2a$10$4bRpgw0QT50CqIb9iuhEOuyL8g2Qo1yc5sIgUe/2WsLYEKzGCmTvy");
    }
}
