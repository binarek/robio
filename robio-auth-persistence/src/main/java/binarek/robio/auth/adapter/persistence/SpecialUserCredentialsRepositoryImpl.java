package binarek.robio.auth.adapter.persistence;

import binarek.robio.auth.SpecialUserCredentialsRepository;
import binarek.robio.auth.adapter.persistence.configuration.AuthBeanNames;
import binarek.robio.auth.model.SpecialUserCredentials;
import binarek.robio.auth.model.SpecialUsername;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static binarek.robio.auth.adapter.persistence.db.tables.SpecialUser.SPECIAL_USER;

@Repository
public class SpecialUserCredentialsRepositoryImpl implements SpecialUserCredentialsRepository {

    private final DSLContext dsl;
    private final SpecialUserRecordsMapper mapper;

    SpecialUserCredentialsRepositoryImpl(@Qualifier(AuthBeanNames.DSL_CONTEXT) DSLContext dsl,
                                         SpecialUserRecordsMapper mapper) {
        this.dsl = dsl;
        this.mapper = mapper;
    }

    @Override
    public void saveIfNotExist(SpecialUserCredentials credentials) {
        final var record = dsl.newRecord(SPECIAL_USER);
        mapper.update(record, credentials);

        dsl.insertInto(SPECIAL_USER)
                .set(record)
                .onConflict(SPECIAL_USER.USERNAME)
                .doNothing()
                .execute();
    }

    @Override
    public Optional<SpecialUserCredentials> getByUsername(SpecialUsername username) {
        return dsl.selectFrom(SPECIAL_USER)
                .where(SPECIAL_USER.USERNAME.eq(username.getValue()))
                .fetchOptional(mapper::toSpecialUserCredentials);
    }
}
