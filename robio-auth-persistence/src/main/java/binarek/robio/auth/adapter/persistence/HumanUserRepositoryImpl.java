package binarek.robio.auth.adapter.persistence;

import binarek.robio.auth.HumanUserRepository;
import binarek.robio.auth.adapter.persistence.configuration.AuthBeanNames;
import binarek.robio.auth.model.HumanUser;
import binarek.robio.auth.model.HumanUsername;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static binarek.robio.auth.adapter.persistence.db.tables.HumanUser.HUMAN_USER;

@Repository
public class HumanUserRepositoryImpl implements HumanUserRepository {

    private final DSLContext dsl;
    private final HumanUserRecordsMapper mapper;

    public HumanUserRepositoryImpl(@Qualifier(AuthBeanNames.DSL_CONTEXT) DSLContext dsl,
                                   HumanUserRecordsMapper mapper) {
        this.dsl = dsl;
        this.mapper = mapper;
    }

    @Override
    public Optional<HumanUser> getByUsername(HumanUsername username) {
        return dsl.selectFrom(HUMAN_USER)
                .where(HUMAN_USER.EMAIL.eq(username.getValue()))
                .fetchOptional(mapper::toHumanUser);
    }
}
