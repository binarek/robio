package binarek.robio.core.persistence.robot;

import binarek.robio.codegen.BaseMapperConfig;
import binarek.robio.common.domain.value.StandardValueMapper;
import binarek.robio.core.domain.robot.*;
import binarek.robio.db.tables.records.RobotRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;

@Mapper(config = BaseMapperConfig.class, uses = StandardValueMapper.class)
public interface RobotRecordMapper {

    LengthUnit DEFAULT_LENGTH_UNIT = LengthUnit.M;
    WeightUnit DEFAULT_WEIGHT_UNIT = WeightUnit.G;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    void updateRecord(@MappingTarget RobotRecord robotRecord, Robot robot);

    @Mapping(target = "id", source = "externalId")
    Robot toRobot(RobotRecord robotRecord);

    default RobotName toRobotName(String name) {
        return RobotName.of(name);
    }

    default String toValue(RobotName name) {
        return name.getValue();
    }

    default Length toLength(BigDecimal length) {
        return Length.of(length, DEFAULT_LENGTH_UNIT);
    }

    default BigDecimal toValue(Length length) {
        return length.convertUnit(DEFAULT_LENGTH_UNIT).getValue();
    }

    default Weight toWeight(BigDecimal weight) {
        return Weight.of(weight, DEFAULT_WEIGHT_UNIT);
    }

    default BigDecimal toValue(Weight weight) {
        return weight.convertUnit(DEFAULT_WEIGHT_UNIT).getValue();
    }
}
