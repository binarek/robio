package binarek.robio.ftl.api.planning;

import binarek.robio.ftl.api.planning.command.InitializeFtlCompetitionPlanCommand;
import binarek.robio.ftl.api.planning.model.FtlCompetitionPlan;
import binarek.robio.ftl.api.planning.model.FtlRobotPlaceholder;
import org.springframework.lang.Nullable;

import java.util.UUID;

public interface FtlCompetitionPlanAppService {

    void initializePlan(InitializeFtlCompetitionPlanCommand command);

    void addRobotToPlan(UUID competitionId, FtlRobotPlaceholder robot);

    void changeRunsLimitPerRobotInPlan(UUID competitionId, @Nullable Integer runsLimitPerRobot);

    void applyPlan(UUID competitionId);

    FtlCompetitionPlan getPlan(UUID competitionId);
}
