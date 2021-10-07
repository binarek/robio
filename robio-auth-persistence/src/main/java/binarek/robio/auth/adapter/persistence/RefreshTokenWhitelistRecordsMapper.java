package binarek.robio.auth.adapter.persistence;

import binarek.robio.auth.AuthMapper;
import binarek.robio.auth.adapter.persistence.db.tables.records.RefreshTokenWhitelistRecord;
import binarek.robio.auth.model.RefreshToken;
import binarek.robio.shared.SharedMapper;
import binarek.robio.util.codegen.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static binarek.robio.util.MapperUtil.mapNullSafe;

@Mapper(config = BaseMapperConfig.class, uses = {SharedMapper.class, AuthMapper.class})
interface RefreshTokenWhitelistRecordsMapper {

    @Mapping(target = "tokenId", source = "claims.tokenId")
    @Mapping(target = "userId", source = "claims.userId")
    @Mapping(target = "expiredAt", source = "claims.expiredAt")
    void update(@MappingTarget RefreshTokenWhitelistRecord record, RefreshToken refreshToken);

    @Nullable
    default LocalDateTime toLocalDateTime(@Nullable Instant instant) {
        return mapNullSafe(instant, instantValue -> LocalDateTime.ofInstant(instantValue, ZoneOffset.UTC));
    }
}
