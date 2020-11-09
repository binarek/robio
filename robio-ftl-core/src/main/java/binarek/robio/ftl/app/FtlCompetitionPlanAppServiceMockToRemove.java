package binarek.robio.ftl.app;

import binarek.robio.ftl.api.planning.FtlCompetitionPlanAppService;
import binarek.robio.ftl.api.planning.model.FtlCompetitionPlan;
import binarek.robio.ftl.api.planning.model.FtlRobotPlaceholder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Deprecated // temporal class, for test purposes
class FtlCompetitionPlanAppServiceMockToRemove implements FtlCompetitionPlanAppService {

    @Override
    public FtlCompetitionPlan getPlan(UUID competitionId) {
        return FtlCompetitionPlan.builder()
                .competitionId(competitionId)
                .runsLimitPerRobot(12)
                .addRobots(FtlRobotPlaceholder.builder()
                        .robotId(UUID.randomUUID())
                        .teamId(UUID.randomUUID())
                        .build())
                .build();
    }
}
