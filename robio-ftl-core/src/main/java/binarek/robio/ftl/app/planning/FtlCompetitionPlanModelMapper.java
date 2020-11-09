package binarek.robio.ftl.app.planning;

import binarek.robio.common.codegen.BaseMapperConfig;
import binarek.robio.ftl.api.planning.model.FtlCompetitionPlan;
import binarek.robio.ftl.api.planning.model.FtlRobotPlaceholder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
interface FtlCompetitionPlanModelMapper {   // TODO null safety in mappings or fix getters resolving

    @Mapping(target = "competitionId", expression = "java(plan.competitionId())")
    @Mapping(target = "robots", expression = "java(map(plan.robots()))")
    @Mapping(target = "runsLimitPerRobot", expression = "java(plan.runsLimitPerRobot())")
    FtlCompetitionPlan map(binarek.robio.ftl.domain.planning.model.FtlCompetitionPlan plan);

    @Mapping(target = "robotId", expression = "java(robot.robotId())")
    @Mapping(target = "teamId", expression = "java(robot.teamId())")
    FtlRobotPlaceholder map(binarek.robio.ftl.domain.planning.model.FtlRobotPlaceholder robot);

    Iterable<FtlRobotPlaceholder> map(Iterable<? extends binarek.robio.ftl.domain.planning.model.FtlRobotPlaceholder> robots);

    default binarek.robio.ftl.domain.planning.model.FtlRobotPlaceholder map(FtlRobotPlaceholder robot) {
        return binarek.robio.ftl.domain.planning.model.FtlRobotPlaceholder.of(robot.robotId(), robot.teamId());
    }
}
