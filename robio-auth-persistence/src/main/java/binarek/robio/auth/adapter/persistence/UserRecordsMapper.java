package binarek.robio.auth.adapter.persistence;

import binarek.robio.auth.AuthMapper;
import binarek.robio.auth.adapter.persistence.db.tables.records.AuthUserRecord;
import binarek.robio.auth.model.ImmutableUser;
import binarek.robio.auth.model.User;
import binarek.robio.shared.SharedMapper;
import binarek.robio.util.codegen.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = BaseMapperConfig.class, uses = {SharedMapper.class, AuthMapper.class})
interface UserRecordsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", source = "hashedPassword")
    @Mapping(target = "version", defaultValue = "0L")
    void update(@MappingTarget AuthUserRecord record, User user);

    @Mapping(target = "hashedPassword", source = "password")
    ImmutableUser toUser(AuthUserRecord record);
}
