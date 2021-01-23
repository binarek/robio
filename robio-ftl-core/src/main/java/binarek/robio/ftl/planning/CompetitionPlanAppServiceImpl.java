package binarek.robio.ftl.planning;

import binarek.robio.ftl.planning.command.ChangePlanRulesCommand;
import binarek.robio.ftl.planning.command.InitializeCompetitionPlanCommand;
import binarek.robio.ftl.planning.command.SearchCompetitionPlanCommand;
import binarek.robio.ftl.planning.exception.CompetitionPlanNotFoundException;
import binarek.robio.ftl.planning.model.CompetitionPlan;
import binarek.robio.ftl.planning.view.CompetitionPlanView;
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
    @Retryable(value = EntityHasChangedException.class)
    public void changePlanRules(ChangePlanRulesCommand command) {
        var plan = getPlan(command.getCompetitionId());
        competitionPlanRepository.save(plan.changeRules(command.getRules()));
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
