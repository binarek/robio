package binarek.robio.ftl.adapter.persistence;

import binarek.robio.ftl.adapter.persistence.db.tables.records.CompetitionRecord;
import binarek.robio.ftl.model.Competition;
import binarek.robio.ftl.model.ImmutableCompetition;
import binarek.robio.shared.SharedMapper;
import binarek.robio.util.codegen.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.lang.Nullable;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

import static binarek.robio.util.MapperUtil.mapNullSafe;

@Mapper(config = BaseMapperConfig.class, uses = {SharedMapper.class, CompetitionRulesJsonTypeMapper.class})
abstract class CompetitionRecordsMapper {

    @Mapping(target = "id", ignore = true)
    abstract void update(@MappingTarget CompetitionRecord record, Competition competition);

    abstract ImmutableCompetition toCompetition(CompetitionRecord competitionRecord);

    @Nullable
    protected ZonedDateTime toZonedDateTime(@Nullable OffsetDateTime offsetDateTime) {
        return mapNullSafe(offsetDateTime, OffsetDateTime::toZonedDateTime);
    }

    @Nullable
    protected OffsetDateTime toOffsetDateTime(@Nullable ZonedDateTime zonedDateTime) {
        return mapNullSafe(zonedDateTime, ZonedDateTime::toOffsetDateTime);
    }
}
