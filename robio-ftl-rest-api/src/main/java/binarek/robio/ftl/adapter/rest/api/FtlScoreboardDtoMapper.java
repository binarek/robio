package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.adapter.rest.api.dto.ScoreboardDto;
import binarek.robio.ftl.view.ScoreboardView;
import binarek.robio.shared.SharedMapper;
import binarek.robio.util.codegen.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class, uses = {SharedMapper.class, FtlRunDtoMapper.class})
interface FtlScoreboardDtoMapper {

    ScoreboardDto toScoreboardDto(ScoreboardView scoreboard);
}
