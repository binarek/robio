package binarek.robio.ftl;

import binarek.robio.ftl.command.ChangeCompetitionRulesCommand;
import binarek.robio.ftl.command.InitializeCompetitionCommand;
import binarek.robio.ftl.command.StartCompetitionCommand;
import binarek.robio.ftl.exception.CompetitionNotFoundException;
import binarek.robio.ftl.model.Competition;
import binarek.robio.ftl.query.CompetitionByIdQuery;
import binarek.robio.ftl.view.CompetitionView;
import binarek.robio.shared.DateTimeProvider;
import binarek.robio.shared.exception.EntityHasChangedException;
import binarek.robio.shared.model.CompetitionId;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class CompetitionAppServiceImpl implements CompetitionAppService {

    private final CompetitionRepository competitionRepository;
    private final CompetitionService competitionService;
    private final DateTimeProvider dateTimeProvider;

    CompetitionAppServiceImpl(CompetitionRepository competitionRepository,
                              CompetitionService competitionService,
                              DateTimeProvider dateTimeProvider) {
        this.competitionRepository = competitionRepository;
        this.competitionService = competitionService;
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void initializeCompetition(InitializeCompetitionCommand command) {
        competitionService.validateIfCanInitializeCompetition(command.getCompetitionId());
        var competition = Competition.initialize(command.getCompetitionId(), command.getRules(), dateTimeProvider.currentBusinessDateTime());
        competitionRepository.save(competition);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Retryable(EntityHasChangedException.class)
    public void startCompetition(StartCompetitionCommand command) {
        final var competitionId = command.getCompetitionId();
        var competition = competitionRepository.getByCompetitionId(competitionId)
                .orElseThrow(() -> CompetitionNotFoundException.of(competitionId));

        competitionService.validateIfCanStartCompetition(competition);
        competition = competition.start(dateTimeProvider.currentBusinessDateTime());
        competitionRepository.save(competition);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Retryable(EntityHasChangedException.class)
    public void changeCompetitionRules(ChangeCompetitionRulesCommand command) {
        var competition = getCompetition(command.getCompetitionId())
                .changeRules(command.getRules());
        competitionRepository.save(competition);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ORGANIZER')")
    public CompetitionView getCompetition(CompetitionByIdQuery query) {
        return getCompetition(query.getCompetitionId());
    }

    private Competition getCompetition(CompetitionId competitionId) {
        return competitionRepository.getByCompetitionId(competitionId)
                .orElseThrow(() -> CompetitionNotFoundException.of(competitionId));
    }
}
