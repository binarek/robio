package binarek.robio.ftl.adapter.rest.api;

import binarek.robio.ftl.adapter.rest.api.dto.AddRunCommandDto;
import binarek.robio.ftl.adapter.rest.api.dto.RunDto;
import binarek.robio.ftl.adapter.rest.api.dto.RunPatchDto;
import binarek.robio.ftl.command.AddRunCommand;
import binarek.robio.ftl.command.EditRunResultCommand;
import binarek.robio.ftl.model.RunTime;
import binarek.robio.ftl.query.RunQuery;
import binarek.robio.ftl.view.RunView;
import binarek.robio.shared.SharedMapper;
import binarek.robio.util.codegen.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.springframework.lang.Nullable;

import java.util.UUID;

import static binarek.robio.util.MapperUtil.mapNullSafe;

@Mapper(config = BaseMapperConfig.class, uses = SharedMapper.class)
interface FtlRunDtoMapper {

    AddRunCommand toAddRunCommandDto(UUID competitionId, UUID robotId, AddRunCommandDto dto);

    EditRunResultCommand toEditRunResultCommand(UUID competitionId, UUID robotId, Integer number, RunPatchDto dto);

    RunQuery toRunQuery(UUID competitionId, UUID robotId, Integer number);

    RunDto toRunDto(RunView run);

    @Nullable
    default RunTime toRunTime(@Nullable Long value) {
        return mapNullSafe(value, RunTime::of);
    }

    @Nullable
    default Long toValue(@Nullable RunTime runTime) {
        return mapNullSafe(runTime, RunTime::getValue);
    }
}
