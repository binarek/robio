package binarek.robio.ftl.planning.app;

import binarek.robio.ftl.planning.api.CompetitionPlanAppService;
import binarek.robio.ftl.planning.api.command.InitializeCompetitionPlanCommand;
import binarek.robio.ftl.planning.api.exception.CompetitionPlanNotFoundException;
import binarek.robio.ftl.planning.api.query.CompetitionPlanView;
import binarek.robio.ftl.planning.domain.CompetitionPlanRepository;
import binarek.robio.ftl.planning.domain.CompetitionPlanService;
import binarek.robio.ftl.planning.domain.model.CompetitionPlan;
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
        var newPlan = CompetitionPlan.newPlan(command.getCompetitionId(), command.getRunsLimitPerRobot());
        competitionPlanRepository.save(newPlan);
    }

    @Override
    public CompetitionPlanView getPlan(UUID competitionId) {
        return competitionPlanRepository.getByCompetitionId(competitionId)
                .orElseThrow(() -> CompetitionPlanNotFoundException.ofCompetitionId(competitionId));
    }
}
