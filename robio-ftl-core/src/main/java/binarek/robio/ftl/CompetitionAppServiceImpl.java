package binarek.robio.ftl;

import binarek.robio.ftl.command.ChangeCompetitionRulesCommand;
import binarek.robio.ftl.command.InitializeCompetitionCommand;
import binarek.robio.ftl.command.SearchCompetitionCommand;
import binarek.robio.ftl.command.StartCompetitionCommand;
import binarek.robio.ftl.exception.CompetitionNotFoundException;
import binarek.robio.ftl.model.Competition;
import binarek.robio.ftl.view.CompetitionView;
import binarek.robio.ftl.view.RobotView;
import binarek.robio.shared.DateTimeProvider;
import binarek.robio.shared.exception.EntityHasChangedException;
import binarek.robio.shared.model.CompetitionId;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
class CompetitionAppServiceImpl implements CompetitionAppService {

    private final CompetitionRepository competitionRepository;
    private final RobotRepository robotRepository;
    private final CompetitionService competitionService;
    private final DateTimeProvider dateTimeProvider;

    CompetitionAppServiceImpl(CompetitionRepository competitionRepository,
                              RobotRepository robotRepository,
                              CompetitionService competitionService,
                              DateTimeProvider dateTimeProvider) {
        this.competitionRepository = competitionRepository;
        this.robotRepository = robotRepository;
        this.competitionService = competitionService;
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    @Transactional
    public void initializeCompetition(InitializeCompetitionCommand command) {
        competitionService.validateIfCanInitializeCompetition(command.getCompetitionId());
        var competition = Competition.initialize(command.getCompetitionId(), command.getRules(), dateTimeProvider.currentZonedDateTime());
        competitionRepository.save(competition);
    }

    @Override
    @Retryable(EntityHasChangedException.class)
    public void startCompetition(StartCompetitionCommand command) {
        final var competitionId = command.getCompetitionId();
        var competition = competitionRepository.getByCompetitionId(competitionId)
                .orElseThrow(() -> CompetitionNotFoundException.of(competitionId));

        competitionService.validateIfCanStartCompetition(competition);
        competition = competition.start(dateTimeProvider.currentZonedDateTime());
        competitionRepository.save(competition);
    }

    @Override
    @Retryable(EntityHasChangedException.class)
    public void changeCompetitionRules(ChangeCompetitionRulesCommand command) {
        var competition = getCompetition(command.getCompetitionId())
                .changeRules(command.getRules());
        competitionRepository.save(competition);
    }

    @Override
    public CompetitionView getCompetition(SearchCompetitionCommand command) {
        return getCompetition(command.getCompetitionId());
    }

    @Override
    public Collection<? extends RobotView> getCompetitionRobots(SearchCompetitionCommand command) {
        final var competitionId = command.getCompetitionId();
        checkIfCompetitionExists(competitionId);
        return robotRepository.getByCompetitionId(competitionId);
    }

    private void checkIfCompetitionExists(CompetitionId competitionId) {
        if (!competitionRepository.existsByCompetitionId(competitionId)) {
            throw CompetitionNotFoundException.of(competitionId);
        }
    }

    private Competition getCompetition(CompetitionId competitionId) {
        return competitionRepository.getByCompetitionId(competitionId)
                .orElseThrow(() -> CompetitionNotFoundException.of(competitionId));
    }
}
