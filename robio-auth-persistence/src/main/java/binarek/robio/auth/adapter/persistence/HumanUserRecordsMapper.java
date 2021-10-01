package binarek.robio.auth.adapter.persistence;

import binarek.robio.auth.adapter.persistence.db.tables.records.HumanUserRecord;
import binarek.robio.auth.model.ImmutableHumanUser;
import binarek.robio.auth.AuthMapper;
import binarek.robio.shared.SharedMapper;
import binarek.robio.util.codegen.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class, uses = {SharedMapper.class, AuthMapper.class})
interface HumanUserRecordsMapper {

    @Mapping(target = "hashedPassword", source = "password")
    ImmutableHumanUser toHumanUser(HumanUserRecord record);
}
