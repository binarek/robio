package binarek.robio.ftl.adapter.persistence;

import binarek.robio.ftl.adapter.persistence.db.tables.records.CompetitionPlanRecord;
import binarek.robio.ftl.model.CompetitionPlan;
import binarek.robio.ftl.model.CompetitionRules;
import binarek.robio.ftl.model.ImmutableCompetitionPlan;
import binarek.robio.shared.SharedMapper;
import binarek.robio.util.codegen.BaseMapperConfig;
import org.jooq.JSON;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.UUID;

@Mapper(config = BaseMapperConfig.class, uses = SharedMapper.class)
abstract class CompetitionPlanRecordsMapper {

    private final FtlJsonTypeMapper<CompetitionRules> competitionRulesTypeMapper = FtlJsonTypeMapper.ofType(CompetitionRules.class);

    @Mapping(target = "id", ignore = true)
    abstract void update(@MappingTarget CompetitionPlanRecord record, CompetitionPlan competitionPlan);

    @Mapping(target = "robots", source = "robotIds")
    abstract ImmutableCompetitionPlan toCompetitionPlan(CompetitionPlanRecord competitionPlanRecord,
                                                        Collection<UUID> robotIds);

    @Nullable
    protected CompetitionRules toCompetitionRules(@Nullable JSON rulesJson) {
        return competitionRulesTypeMapper.toObject(rulesJson);
    }

    @Nullable
    protected JSON toCompetitionRules(@Nullable CompetitionRules rules) {
        return competitionRulesTypeMapper.toJson(rules);
    }
}
