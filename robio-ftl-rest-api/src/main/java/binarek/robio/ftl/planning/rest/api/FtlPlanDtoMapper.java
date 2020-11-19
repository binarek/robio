package binarek.robio.ftl.planning.rest.api;

import binarek.robio.common.codegen.BaseMapperConfig;
import binarek.robio.ftl.planning.api.command.InitializeCompetitionPlanCommand;
import binarek.robio.ftl.planning.api.query.CompetitionPlanView;
import binarek.robio.ftl.planning.rest.api.dto.CompetitionPlanDto;
import binarek.robio.ftl.planning.rest.api.dto.InitializeCompetitionPlanCommandDto;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
interface FtlPlanDtoMapper {

    InitializeCompetitionPlanCommand toInitializeCompetitionPlanCommand(InitializeCompetitionPlanCommandDto dto);

    CompetitionPlanDto toCompetitionPlanDto(CompetitionPlanView view);
}
