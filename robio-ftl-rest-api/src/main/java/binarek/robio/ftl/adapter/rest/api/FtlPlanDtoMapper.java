package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.adapter.rest.api.dto.CompetitionPlanDto;
import binarek.robio.ftl.adapter.rest.api.dto.CompetitionRulesDto;
import binarek.robio.ftl.adapter.rest.api.dto.InitializeCompetitionPlanCommandDto;
import binarek.robio.ftl.model.CompetitionRules;
import binarek.robio.ftl.planning.command.ChangePlanRulesCommand;
import binarek.robio.ftl.planning.command.InitializeCompetitionPlanCommand;
import binarek.robio.ftl.planning.command.SearchCompetitionPlanCommand;
import binarek.robio.ftl.planning.view.CompetitionPlanView;
import binarek.robio.shared.SharedMapper;
import binarek.robio.util.codegen.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(config = BaseMapperConfig.class, uses = SharedMapper.class)
interface FtlPlanDtoMapper {

    InitializeCompetitionPlanCommand toInitializeCompetitionPlanCommand(InitializeCompetitionPlanCommandDto dto);

    SearchCompetitionPlanCommand toSearchCompetitionPlanCommand(UUID competitionId);

    @Mapping(target = "rules", source = "dto")
    ChangePlanRulesCommand toChangePlanRulesCommand(UUID competitionId, CompetitionRulesDto dto);

    CompetitionRules toCompetitionRules(CompetitionRulesDto dto);

    CompetitionPlanDto toCompetitionPlanDto(CompetitionPlanView view);
}
