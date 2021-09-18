package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.adapter.rest.api.dto.CompetitionPlanDto;
import binarek.robio.ftl.adapter.rest.api.dto.CompetitionPlanRobotDto;
import binarek.robio.ftl.adapter.rest.api.dto.CompetitionRulesDto;
import binarek.robio.ftl.adapter.rest.api.dto.InitializeCompetitionPlanCommandDto;
import binarek.robio.ftl.command.*;
import binarek.robio.ftl.model.CompetitionRules;
import binarek.robio.ftl.view.CompetitionPlanView;
import binarek.robio.shared.SharedMapper;
import binarek.robio.util.codegen.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

import java.util.Set;
import java.util.UUID;

@Mapper(config = BaseMapperConfig.class, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = SharedMapper.class, imports = Set.class)
interface FtlCompetitionPlanDtoMapper {

    InitializeCompetitionPlanCommand toInitializeCompetitionPlanCommand(InitializeCompetitionPlanCommandDto dto);

    SearchCompetitionPlanCommand toSearchCompetitionPlanCommand(UUID competitionId);

    @Mapping(target = "robots", expression = "java(Set.of(sharedMapper.toRobotId(robotId)))")
    AddRobotsToCompetitionPlanCommand toAddRobotsFromCompetitionPlanCommand(UUID competitionId, UUID robotId);

    @Mapping(target = "robots", expression = "java(Set.of(sharedMapper.toRobotId(robotId)))")
    RemoveRobotsFromCompetitionPlanCommand toRemoveRobotsFromCompetitionPlanCommand(UUID competitionId, UUID robotId);

    @Mapping(target = "rules", source = "dto")
    ChangeCompetitionPlanRulesCommand toChangePlanRulesCommand(UUID competitionId, CompetitionRulesDto dto);

    CompetitionRules toCompetitionRules(CompetitionRulesDto dto);

    CompetitionPlanDto toCompetitionPlanDto(CompetitionPlanView view);

    CompetitionPlanRobotDto toCompetitionPlanRobotDto(UUID robotId);
}
