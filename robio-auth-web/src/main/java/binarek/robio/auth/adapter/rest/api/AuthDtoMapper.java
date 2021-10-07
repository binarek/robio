package binarek.robio.auth.adapter.rest.api;

import binarek.robio.auth.adapter.rest.api.dto.TokensDto;
import binarek.robio.auth.view.TokensPairView;
import binarek.robio.util.codegen.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
interface AuthDtoMapper {

    @Mapping(target = "accessToken", source = "accessToken.jwt")
    @Mapping(target = "refreshToken", source = "refreshToken.jwt")
    TokensDto toTokensDto(TokensPairView tokensPair);
}
