package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.common.codegen.BaseMapperConfig;
import binarek.robio.ftl.adapter.rest.api.dto.CompetitionPlanDto;
import binarek.robio.ftl.adapter.rest.api.dto.InitializeCompetitionPlanCommandDto;
import binarek.robio.ftl.planning.command.InitializeCompetitionPlanCommand;
import binarek.robio.ftl.planning.view.CompetitionPlanView;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
interface FtlPlanDtoMapper {

    InitializeCompetitionPlanCommand toInitializeCompetitionPlanCommand(InitializeCompetitionPlanCommandDto dto);

    CompetitionPlanDto toCompetitionPlanDto(CompetitionPlanView view);
}
