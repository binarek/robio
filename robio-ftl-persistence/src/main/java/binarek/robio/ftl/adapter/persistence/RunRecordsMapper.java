package binarek.robio.ftl.adapter.persistence;

import binarek.robio.ftl.adapter.persistence.db.tables.records.RunRecord;
import binarek.robio.ftl.model.ImmutableRun;
import binarek.robio.ftl.model.Run;
import binarek.robio.ftl.model.RunTime;
import binarek.robio.shared.SharedMapper;
import binarek.robio.util.codegen.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.lang.Nullable;

import static binarek.robio.util.MapperUtil.mapNullSafe;

@Mapper(config = BaseMapperConfig.class, uses = SharedMapper.class)
interface RunRecordsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    void update(@MappingTarget RunRecord record, Run run);

    ImmutableRun toRun(RunRecord record);

    @Nullable
    default RunTime toRunTime(@Nullable Long value) {
        return mapNullSafe(value, RunTime::of);
    }

    @Nullable
    default Long toValue(@Nullable RunTime runTime) {
        return mapNullSafe(runTime, RunTime::getValue);
    }
}
