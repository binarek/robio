package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.adapter.rest.api.dto.CompetitionDto;
import binarek.robio.ftl.adapter.rest.api.dto.StartCompetitionCommandDto;
import binarek.robio.ftl.command.StartCompetitionCommand;
import binarek.robio.ftl.view.CompetitionView;
import binarek.robio.shared.SharedMapper;
import binarek.robio.util.codegen.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class, uses = SharedMapper.class)
interface FtlCompetitionDtoMapper {

    StartCompetitionCommand toStartCompetitionCommand(StartCompetitionCommandDto dto);

    CompetitionDto toCompetitionDto(CompetitionView competition);
}
