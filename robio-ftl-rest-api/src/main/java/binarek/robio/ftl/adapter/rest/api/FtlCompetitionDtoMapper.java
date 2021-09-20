package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.adapter.rest.api.dto.CompetitionDto;
import binarek.robio.ftl.adapter.rest.api.dto.CompetitionRulesDto;
import binarek.robio.ftl.adapter.rest.api.dto.InitializeCompetitionCommandDto;
import binarek.robio.ftl.command.ChangeCompetitionRulesCommand;
import binarek.robio.ftl.command.InitializeCompetitionCommand;
import binarek.robio.ftl.command.StartCompetitionCommand;
import binarek.robio.ftl.query.CompetitionByIdQuery;
import binarek.robio.ftl.view.CompetitionView;
import binarek.robio.shared.SharedMapper;
import binarek.robio.util.codegen.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

import java.util.UUID;

@Mapper(config = BaseMapperConfig.class, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = SharedMapper.class)
interface FtlCompetitionDtoMapper {

    InitializeCompetitionCommand toInitializeCompetitionCommand(InitializeCompetitionCommandDto dto);

    StartCompetitionCommand toStartCompetitionCommand(UUID competitionId);

    @Mapping(target = "rules", source = "dto")
    ChangeCompetitionRulesCommand toChangeCompetitionRulesCommand(UUID competitionId, CompetitionRulesDto dto);

    CompetitionByIdQuery toCompetitionByIdQuery(UUID competitionId);

    CompetitionDto toCompetitionDto(CompetitionView competition);
}
