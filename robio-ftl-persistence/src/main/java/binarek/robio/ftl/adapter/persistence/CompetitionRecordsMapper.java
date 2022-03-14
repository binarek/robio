package binarek.robio.ftl.adapter.persistence;

import binarek.robio.ftl.adapter.persistence.db.tables.records.CompetitionRecord;
import binarek.robio.ftl.model.Competition;
import binarek.robio.ftl.model.ImmutableCompetition;
import binarek.robio.shared.SharedMapper;
import binarek.robio.util.codegen.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = BaseMapperConfig.class,
        uses = {SharedMapper.class, SharedPersistenceMapper.class, CompetitionRulesJsonTypeMapper.class})
abstract class CompetitionRecordsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    abstract void update(@MappingTarget CompetitionRecord record, Competition competition);

    abstract ImmutableCompetition toCompetition(CompetitionRecord competitionRecord);
}
