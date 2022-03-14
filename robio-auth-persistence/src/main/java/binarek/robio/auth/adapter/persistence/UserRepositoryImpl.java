package binarek.robio.auth.adapter.persistence;

import binarek.robio.auth.UserRepository;
import binarek.robio.auth.adapter.persistence.configuration.AuthBeanNames;
import binarek.robio.auth.model.User;
import binarek.robio.auth.model.UserRole;
import binarek.robio.auth.model.Username;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static binarek.robio.auth.adapter.persistence.db.tables.AuthUser.AUTH_USER;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final DSLContext dsl;
    private final UserRecordsMapper mapper;

    public UserRepositoryImpl(@Qualifier(AuthBeanNames.DSL_CONTEXT) DSLContext dsl,
                              UserRecordsMapper mapper) {
        this.dsl = dsl;
        this.mapper = mapper;
    }

    @Override
    public void save(User user) {
        final var record = dsl.newRecord(AUTH_USER);
        mapper.update(record, user);

        dsl.insertInto(AUTH_USER)
                .set(record)
                .onConflict(AUTH_USER.USERNAME)
                .doUpdate()
                .set(record)
                .execute();
    }

    @Override
    public Optional<User> getByUsername(Username username) {
        return dsl.selectFrom(AUTH_USER)
                .where(AUTH_USER.USERNAME.eq(username.getValue()))
                .fetchOptional(mapper::toUser);
    }

    @Override
    public Optional<UserRole> getRoleByUsername(Username username) {
        return dsl.select(AUTH_USER.ROLE)
                .from(AUTH_USER)
                .where(AUTH_USER.USERNAME.eq(username.getValue()))
                .fetchOptional(record -> UserRole.valueOf(record.value1()));
    }
}
