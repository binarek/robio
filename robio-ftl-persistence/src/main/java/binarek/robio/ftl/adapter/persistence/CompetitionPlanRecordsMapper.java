package binarek.robio.ftl.adapter.persistence;

import binarek.robio.ftl.adapter.persistence.db.tables.records.CompetitionPlanRecord;
import binarek.robio.ftl.adapter.persistence.db.tables.records.CompetitionPlanRobotRecord;
import binarek.robio.ftl.model.CompetitionRules;
import binarek.robio.ftl.planning.model.CompetitionPlan;
import binarek.robio.ftl.planning.model.ImmutableCompetitionPlan;
import binarek.robio.ftl.planning.model.RobotPlaceholder;
import binarek.robio.shared.SharedMapper;
import binarek.robio.util.codegen.BaseMapperConfig;
import org.jooq.JSON;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.lang.Nullable;

import java.util.List;

@Mapper(config = BaseMapperConfig.class, uses = SharedMapper.class)
abstract class CompetitionPlanRecordsMapper {

    private final FtlJsonTypeMapper<CompetitionRules> CompetitionRulesTypeMapper = FtlJsonTypeMapper.ofType(CompetitionRules.class);

    @Mapping(target = "id", ignore = true)
    abstract void update(@MappingTarget CompetitionPlanRecord record, CompetitionPlan competitionPlan);

    abstract void update(@MappingTarget CompetitionPlanRobotRecord record, RobotPlaceholder robotPlaceholder, Long competitionPlanId);

    @Mapping(target = "robots", source = "robotRecords")
    abstract ImmutableCompetitionPlan toCompetitionPlan(CompetitionPlanRecord competitionPlanRecord,
                                                        List<CompetitionPlanRobotRecord> robotRecords);

    abstract RobotPlaceholder toRobotPlaceholder(CompetitionPlanRobotRecord robotRecord);

    @Nullable
    protected CompetitionRules toCompetitionRules(@Nullable JSON rulesJson) {
        return CompetitionRulesTypeMapper.toObject(rulesJson);
    }

    @Nullable
    protected JSON toCompetitionRules(@Nullable CompetitionRules rules) {
        return CompetitionRulesTypeMapper.toJson(rules);
    }
}
