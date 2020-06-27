package binarek.robio.core.persistence.robot;

import binarek.robio.codegen.BaseMapperConfig;
import binarek.robio.common.domain.value.StandardValueMapper;
import binarek.robio.core.domain.robot.*;
import binarek.robio.db.tables.records.RobotRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

import static binarek.robio.common.util.MapperUtil.mapNullSafe;

@Mapper(config = BaseMapperConfig.class, uses = StandardValueMapper.class)
public interface RobotRecordMapper {

    LengthUnit DEFAULT_LENGTH_UNIT = LengthUnit.M;
    WeightUnit DEFAULT_WEIGHT_UNIT = WeightUnit.G;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    void updateRecord(@MappingTarget RobotRecord robotRecord, Robot robot);

    @Mapping(target = "id", source = "externalId")
    Robot toRobot(RobotRecord robotRecord);

    @Nullable
    default RobotName toRobotName(@Nullable String name) {
        return mapNullSafe(name, RobotName::of);
    }

    @Nullable
    default String toValue(@Nullable RobotName name) {
        return mapNullSafe(name, RobotName::getValue);
    }

    @Nullable
    default Length toLength(@Nullable BigDecimal length) {
        return mapNullSafe(length, value -> Length.of(value, DEFAULT_LENGTH_UNIT));
    }

    @Nullable
    default BigDecimal toValue(@Nullable Length length) {
        return mapNullSafe(length, value -> value.convertUnit(DEFAULT_LENGTH_UNIT).getValue());
    }

    @Nullable
    default Weight toWeight(@Nullable BigDecimal weight) {
        return mapNullSafe(weight, value -> Weight.of(value, DEFAULT_WEIGHT_UNIT));
    }

    @Nullable
    default BigDecimal toValue(@Nullable Weight weight) {
        return mapNullSafe(weight, value -> value.convertUnit(DEFAULT_WEIGHT_UNIT).getValue());
    }
}
