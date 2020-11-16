package binarek.robio.ftl.planning.app;

import binarek.robio.ftl.planning.api.CompetitionPlanAppService;
import binarek.robio.ftl.planning.api.command.InitializeCompetitionPlanCommand;
import binarek.robio.ftl.planning.api.exception.CompetitionPlanNotFoundException;
import binarek.robio.ftl.planning.api.query.CompetitionPlanView;
import binarek.robio.ftl.planning.domain.CompetitionPlanRepository;
import binarek.robio.ftl.planning.domain.model.CompetitionPlan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
class CompetitionPlanAppServiceImpl implements CompetitionPlanAppService {

    private final CompetitionPlanRepository competitionPlanRepository;
    private final FtlPlanningModelMapper modelMapper;

    CompetitionPlanAppServiceImpl(CompetitionPlanRepository competitionPlanRepository, FtlPlanningModelMapper modelMapper) {
        this.competitionPlanRepository = competitionPlanRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void initializePlan(InitializeCompetitionPlanCommand command) {
        var newPlan = CompetitionPlan.newPlan(command.competitionId(), command.runsLimitPerRobot());
        competitionPlanRepository.save(newPlan);
    }

    @Override
    public CompetitionPlanView getPlan(UUID competitionId) {
        return competitionPlanRepository.getByCompetitionId(competitionId)
                .map(modelMapper::toCompetitionPlanView)
                .orElseThrow(() -> CompetitionPlanNotFoundException.ofCompetitionId(competitionId));
    }
}
