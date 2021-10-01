package binarek.robio.auth.model;

public interface User {

    Username getUsername();

    HashedPassword getHashedPassword();

    UserRole getRole();
}
