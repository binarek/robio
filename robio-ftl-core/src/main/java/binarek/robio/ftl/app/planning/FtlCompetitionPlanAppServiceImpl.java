package binarek.robio.ftl.app.planning;

import binarek.robio.ftl.api.planning.FtlCompetitionPlanAppService;
import binarek.robio.ftl.api.planning.command.InitializeFtlCompetitionPlanCommand;
import binarek.robio.ftl.api.planning.model.FtlCompetitionPlan;
import binarek.robio.ftl.api.planning.model.FtlRobotPlaceholder;
import binarek.robio.ftl.domain.execution.FtlCompetitionRepository;
import binarek.robio.ftl.domain.planning.FtlCompetitionPlanRepository;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

class FtlCompetitionPlanAppServiceImpl implements FtlCompetitionPlanAppService {

    private final FtlCompetitionPlanModelMapper modelMapper;
    private final FtlCompetitionPlanRepository ftlCompetitionPlanRepository;
    private final FtlCompetitionRepository ftlCompetitionRepository;

    FtlCompetitionPlanAppServiceImpl(FtlCompetitionPlanModelMapper modelMapper,
                                     FtlCompetitionPlanRepository ftlCompetitionPlanRepository,
                                     FtlCompetitionRepository ftlCompetitionRepository) {
        this.modelMapper = modelMapper;
        this.ftlCompetitionPlanRepository = ftlCompetitionPlanRepository;
        this.ftlCompetitionRepository = ftlCompetitionRepository;
    }

    @Override
    public void initializePlan(InitializeFtlCompetitionPlanCommand command) {
        var newPlan = newDomainPlan(command);
        ftlCompetitionPlanRepository.save(newPlan);
    }

    @Override
    public void addRobotToPlan(UUID competitionId, FtlRobotPlaceholder robot) {
        var updatedPlan = getDomainPlan(competitionId).addRobot(modelMapper.map(robot));
        ftlCompetitionPlanRepository.save(updatedPlan);
    }

    @Override
    public void changeRunsLimitPerRobotInPlan(UUID competitionId, @Nullable Integer runsLimitPerRobot) {
        var updatedPlan = getDomainPlan(competitionId).changeRunsLimitPerRobot(runsLimitPerRobot);
        ftlCompetitionPlanRepository.save(updatedPlan);
    }

    @Override
    @Transactional
    public void applyPlan(UUID competitionId) {
        // TODO check if already exists
        var competition = getDomainPlan(competitionId).apply();
        ftlCompetitionRepository.save(competition);
    }

    @Override
    public FtlCompetitionPlan getPlan(UUID competitionId) {
        return modelMapper.map(getDomainPlan(competitionId));
    }

    private binarek.robio.ftl.domain.planning.model.FtlCompetitionPlan getDomainPlan(UUID competitionId) {
        return ftlCompetitionPlanRepository.getByCompetitionId(competitionId)
                .orElseThrow(); // TODO
    }

    private binarek.robio.ftl.domain.planning.model.FtlCompetitionPlan newDomainPlan(InitializeFtlCompetitionPlanCommand command) {
        return binarek.robio.ftl.domain.planning.model.FtlCompetitionPlan.newPlan(
                command.competitionId(), command.runsLimitPerRobot());
    }
}
