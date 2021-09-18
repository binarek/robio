package binarek.robio.ftl;

import binarek.robio.ftl.command.ChangeCompetitionPlanRulesCommand;
import binarek.robio.ftl.command.InitializeCompetitionPlanCommand;
import binarek.robio.ftl.command.SearchCompetitionPlanCommand;
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

    private CompetitionPlan getPlan(CompetitionId competitionId) {
        return competitionPlanRepository.getByCompetitionId(competitionId)
                .orElseThrow(() -> CompetitionPlanNotFoundException.of(competitionId));
    }
}
