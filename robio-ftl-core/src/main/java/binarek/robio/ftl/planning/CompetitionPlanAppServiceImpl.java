package binarek.robio.ftl.planning;

import binarek.robio.ftl.planning.command.InitializeCompetitionPlanCommand;
import binarek.robio.ftl.planning.exception.CompetitionPlanNotFoundException;
import binarek.robio.ftl.planning.model.CompetitionPlan;
import binarek.robio.ftl.planning.view.CompetitionPlanView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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
    public CompetitionPlanView getPlan(UUID competitionId) {
        return competitionPlanRepository.getByCompetitionId(competitionId)
                .orElseThrow(() -> CompetitionPlanNotFoundException.ofCompetitionId(competitionId));
    }
}
