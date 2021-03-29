package binarek.robio.ftl;

import binarek.robio.ftl.command.*;
import binarek.robio.ftl.exception.CompetitionPlanNotFoundException;
import binarek.robio.ftl.model.CompetitionPlan;
import binarek.robio.ftl.view.CompetitionPlanView;
import binarek.robio.shared.exception.EntityHasChangedException;
import binarek.robio.shared.model.CompetitionId;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class CompetitionPlanAppServiceImpl implements CompetitionPlanAppService {

    private final CompetitionPlanRepository competitionPlanRepository;
    private final CompetitionPlanService competitionPlanService;

    CompetitionPlanAppServiceImpl(CompetitionPlanRepository competitionPlanRepository,
                                  CompetitionPlanService competitionPlanService) {
        this.competitionPlanRepository = competitionPlanRepository;
        this.competitionPlanService = competitionPlanService;
    }

    @Override
    @Transactional
    public void initializePlan(InitializeCompetitionPlanCommand command) {
        competitionPlanService.validateIfCanInitializeCompetitionPlan(command.getCompetitionId());
        var newPlan = CompetitionPlan.newPlan(command.getCompetitionId(), command.getRules());
        competitionPlanRepository.save(newPlan);
    }

    @Override
    @Retryable(EntityHasChangedException.class)
    public void changePlanRules(ChangeCompetitionPlanRulesCommand command) {
        var plan = getPlan(command.getCompetitionId());
        competitionPlanRepository.save(plan.changeRules(command.getRules()));
    }

    @Override
    @Retryable(EntityHasChangedException.class)
    public void addRobots(AddRobotsToCompetitionPlanCommand command) {
        var plan = getPlan(command.getCompetitionId());
        competitionPlanRepository.save(plan.addRobots(command.getRobots()));
    }

    @Override
    @Retryable(EntityHasChangedException.class)
    public void removeRobots(RemoveRobotsFromCompetitionPlanCommand command) {
        var plan = getPlan(command.getCompetitionId());
        competitionPlanRepository.save(plan.removeRobots(command.getRobots()));
    }

    @Override
    public CompetitionPlanView getPlan(SearchCompetitionPlanCommand command) {
        return getPlan(command.getCompetitionId());
    }

    private CompetitionPlan getPlan(CompetitionId competitionId) {
        return competitionPlanRepository.getByCompetitionId(competitionId)
                .orElseThrow(() -> CompetitionPlanNotFoundException.of(competitionId));
    }
}
