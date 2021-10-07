package binarek.robio.auth.adapter.persistence;

import binarek.robio.auth.RefreshTokenWhitelistRepository;
import binarek.robio.auth.adapter.persistence.configuration.AuthBeanNames;
import binarek.robio.auth.model.RefreshToken;
import binarek.robio.auth.model.RefreshTokenId;
import binarek.robio.auth.model.UserId;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import static binarek.robio.auth.adapter.persistence.db.tables.RefreshTokenWhitelist.REFRESH_TOKEN_WHITELIST;

@Repository
public class RefreshTokenWhitelistRepositoryImpl implements RefreshTokenWhitelistRepository {

    private final DSLContext dsl;
    private final RefreshTokenWhitelistRecordsMapper mapper;

    public RefreshTokenWhitelistRepositoryImpl(@Qualifier(AuthBeanNames.DSL_CONTEXT) DSLContext dsl,
                                               RefreshTokenWhitelistRecordsMapper mapper) {
        this.dsl = dsl;
        this.mapper = mapper;
    }

    @Override
    public void add(RefreshToken refreshToken) {
        final var record = dsl.newRecord(REFRESH_TOKEN_WHITELIST);
        mapper.update(record, refreshToken);

        dsl.insertInto(REFRESH_TOKEN_WHITELIST)
                .set(record)
                .execute();
    }

    @Override
    public boolean removeByTokenId(RefreshTokenId refreshTokenId) {
        final int rowsDeleted = dsl.deleteFrom(REFRESH_TOKEN_WHITELIST)
                .where(REFRESH_TOKEN_WHITELIST.TOKEN_ID.eq(refreshTokenId.getValue()))
                .execute();
        return rowsDeleted > 0;
    }

    @Override
    public void removeAllByUserId(UserId userId) {
        dsl.deleteFrom(REFRESH_TOKEN_WHITELIST)
                .where(REFRESH_TOKEN_WHITELIST.USER_ID.eq(userId.getValue()))
                .execute();
    }
}
