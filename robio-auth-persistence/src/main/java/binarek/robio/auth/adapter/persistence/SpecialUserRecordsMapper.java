package binarek.robio.auth.adapter.persistence;

import binarek.robio.auth.AuthMapper;
import binarek.robio.auth.adapter.persistence.db.tables.records.SpecialUserRecord;
import binarek.robio.auth.model.ImmutableSpecialUserCredentials;
import binarek.robio.auth.model.SpecialUserCredentials;
import binarek.robio.shared.SharedMapper;
import binarek.robio.util.codegen.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = BaseMapperConfig.class, uses = {SharedMapper.class, AuthMapper.class})
interface SpecialUserRecordsMapper {

    @Mapping(target = "password", source = "hashedPassword")
    @Mapping(target = "version", defaultValue = "0L")
    void update(@MappingTarget SpecialUserRecord record, SpecialUserCredentials specialUserCredentials);

    @Mapping(target = "hashedPassword", source = "password")
    ImmutableSpecialUserCredentials toSpecialUserCredentials(SpecialUserRecord record);
}
