package binarek.robio.ftl;

import binarek.robio.ftl.command.SearchCompetitionCommand;
import binarek.robio.ftl.command.StartCompetitionCommand;
import binarek.robio.ftl.exception.CompetitionNotFoundException;
import binarek.robio.ftl.exception.CompetitionPlanNotFoundException;
import binarek.robio.ftl.model.Competition;
import binarek.robio.ftl.view.CompetitionView;
import binarek.robio.shared.DateTimeProvider;
import binarek.robio.shared.model.CompetitionId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class CompetitionAppServiceImpl implements CompetitionAppService {

    private final CompetitionPlanRepository competitionPlanRepository;
    private final CompetitionRepository competitionRepository;
    private final CompetitionService competitionService;
    private final DateTimeProvider dateTimeProvider;

    CompetitionAppServiceImpl(CompetitionPlanRepository competitionPlanRepository,
                              CompetitionRepository competitionRepository,
                              CompetitionService competitionService,
                              DateTimeProvider dateTimeProvider) {
        this.competitionPlanRepository = competitionPlanRepository;
        this.competitionRepository = competitionRepository;
        this.competitionService = competitionService;
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    @Transactional
    public void startCompetition(StartCompetitionCommand command) {
        final var competitionId = command.getCompetitionId();
        final var competitionPlan = competitionPlanRepository.getByCompetitionId(competitionId)
                .orElseThrow(() -> CompetitionPlanNotFoundException.of(competitionId));

        competitionService.checkIfCanStartCompetition(competitionPlan);
        final var competition = Competition.start(competitionPlan, dateTimeProvider.currentZonedDateTime());

        competitionRepository.save(competition);
        competitionPlanRepository.deleteByCompetitionId(competitionId);
    }

    @Override
    public CompetitionView getCompetition(SearchCompetitionCommand command) {
        return getCompetition(command.getCompetitionId());
    }

    private Competition getCompetition(CompetitionId competitionId) {
        return competitionRepository.getByCompetitionId(competitionId)
                .orElseThrow(() -> CompetitionNotFoundException.of(competitionId));
    }
}
