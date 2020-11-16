package binarek.robio.ftl.planning.app;

import binarek.robio.common.codegen.BaseMapperConfig;
import binarek.robio.ftl.planning.api.query.CompetitionPlanView;
import binarek.robio.ftl.planning.domain.model.CompetitionPlan;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
interface FtlPlanningModelMapper {

    CompetitionPlanView toCompetitionPlanView(CompetitionPlan competitionPlan);
}
