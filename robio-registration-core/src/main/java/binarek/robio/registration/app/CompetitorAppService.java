package binarek.robio.registration.app;

import binarek.robio.common.persistence.EntityChangedException;
import binarek.robio.registration.domain.competitor.*;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompetitorAppService {

    private final CompetitorService competitorService;
    private final CompetitorRepository competitorRepository;
    private final CompetitorIdFactory competitorIdFactory;

    public CompetitorAppService(CompetitorService competitorService,
                                CompetitorRepository competitorRepository,
                                CompetitorIdFactory competitorIdFactory) {
        this.competitorService = competitorService;
        this.competitorRepository = competitorRepository;
        this.competitorIdFactory = competitorIdFactory;
    }

    public Competitor getCompetitorById(CompetitorId competitorId) {
        return competitorRepository.getById(competitorId)
                .orElseThrow(() -> CompetitorNotExistsException.ofId(competitorId));
    }

    public Competitor getCompetitorByEmail(Email email) {
        return competitorRepository.getByEmail(email)
                .orElseThrow(() -> CompetitorNotExistsException.ofEmail(email));
    }

    @Transactional
    public void createCompetitor(CreateCompetitorCommand command) {
        var competitorId = competitorIdFactory.generateCompetitorId();
        var competitor = Competitor.newCompetitor(competitorId,
                command.getEmail(),
                command.getFirstName(),
                command.getLastName(),
                command.isUnderage());
        competitorService.validateForCreation(competitor);
        competitorRepository.save(competitor);
    }

    @Retryable(EntityChangedException.class)
    public void approveByOwner(CompetitorId competitorId) {
        var competitor = getCompetitorById(competitorId);
        competitorRepository.save(competitor.approveByOwner());
    }

    @Retryable(EntityChangedException.class)
    public void approveByParent(CompetitorId competitorId) {
        var competitor = getCompetitorById(competitorId);
        competitorRepository.save(competitor.approveByParent());
    }
}
