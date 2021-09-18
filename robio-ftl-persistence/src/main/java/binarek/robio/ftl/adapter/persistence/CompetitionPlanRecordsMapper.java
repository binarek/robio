package binarek.robio.ftl.adapter.persistence;

import binarek.robio.ftl.adapter.persistence.db.tables.records.CompetitionPlanRecord;
import binarek.robio.ftl.model.CompetitionPlan;
import binarek.robio.ftl.model.ImmutableCompetitionPlan;
import binarek.robio.shared.SharedMapper;
import binarek.robio.util.codegen.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = BaseMapperConfig.class, uses = {SharedMapper.class, CompetitionRulesJsonTypeMapper.class})
abstract class CompetitionPlanRecordsMapper {

    @Mapping(target = "id", ignore = true)
    abstract void update(@MappingTarget CompetitionPlanRecord record, CompetitionPlan competitionPlan);

    abstract ImmutableCompetitionPlan toCompetitionPlan(CompetitionPlanRecord competitionPlanRecord);
}
