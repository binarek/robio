package binarek.robio.ftl;

import binarek.robio.ftl.command.ChangeCompetitionPlanRulesCommand;
import binarek.robio.ftl.command.InitializeCompetitionPlanCommand;
import binarek.robio.ftl.command.SearchCompetitionPlanCommand;
import binarek.robio.ftl.exception.CompetitionPlanNotFoundException;
import binarek.robio.ftl.model.CompetitionPlan;
import binarek.robio.ftl.view.CompetitionPlanView;
import binarek.robio.ftl.view.RobotView;
import binarek.robio.shared.exception.EntityHasChangedException;
import binarek.robio.shared.model.CompetitionId;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
class CompetitionPlanAppServiceImpl implements CompetitionPlanAppService {

    private final CompetitionPlanRepository competitionPlanRepository;
    private final RobotRepository robotRepository;
    private final CompetitionPlanService competitionPlanService;

    CompetitionPlanAppServiceImpl(CompetitionPlanRepository competitionPlanRepository,
                                  RobotRepository robotRepository,
                                  CompetitionPlanService competitionPlanService) {
        this.competitionPlanRepository = competitionPlanRepository;
        this.robotRepository = robotRepository;
        this.competitionPlanService = competitionPlanService;
    }

    @Override
    @Transactional
    public void initializePlan(InitializeCompetitionPlanCommand command) {
        competitionPlanService.validateIfCanInitializeCompetitionPlan(command.getCompetitionId());
        var plan = CompetitionPlan.initializePlan(command.getCompetitionId(), command.getRules());
        competitionPlanRepository.save(plan);
    }

    @Override
    @Retryable(EntityHasChangedException.class)
    public void changePlanRules(ChangeCompetitionPlanRulesCommand command) {
        var plan = getPlan(command.getCompetitionId())
                .changeRules(command.getRules());
        competitionPlanRepository.save(plan);
    }

    @Override
    public CompetitionPlanView getPlan(SearchCompetitionPlanCommand command) {
        return getPlan(command.getCompetitionId());
    }

    @Override
    public Collection<? extends RobotView> getPlanRobots(SearchCompetitionPlanCommand command) {
        final var competitionId = command.getCompetitionId();
        checkIfPlanExists(competitionId);
        return robotRepository.getByCompetitionId(competitionId);
    }

    private void checkIfPlanExists(CompetitionId competitionId) {
        if (!competitionPlanRepository.existsByCompetitionId(competitionId)) {
            throw CompetitionPlanNotFoundException.of(competitionId);
        }
    }

    private CompetitionPlan getPlan(CompetitionId competitionId) {
        return competitionPlanRepository.getByCompetitionId(competitionId)
                .orElseThrow(() -> CompetitionPlanNotFoundException.of(competitionId));
    }
}
