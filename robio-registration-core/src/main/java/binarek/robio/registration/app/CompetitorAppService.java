package binarek.robio.registration.app;

import binarek.robio.common.domain.event.DomainEventPublisher;
import binarek.robio.registration.domain.competitor.*;
import org.springframework.stereotype.Service;

@Service
public class CompetitorAppService {

    private final CompetitorRepository competitorRepository;
    private final CompetitorIdFactory competitorIdentityFactory;
    private final DomainEventPublisher domainEventPublisher;

    public CompetitorAppService(CompetitorRepository competitorRepository,
                                CompetitorIdFactory competitorIdentityFactory,
                                DomainEventPublisher domainEventPublisher) {
        this.competitorRepository = competitorRepository;
        this.competitorIdentityFactory = competitorIdentityFactory;
        this.domainEventPublisher = domainEventPublisher;
    }

    public Competitor getCompetitor(CompetitorId competitorId) {
        return competitorRepository.getById(competitorId)
                .orElseThrow(() -> new CompetitorWithIdNotExistsException(competitorId));
    }

    public CompetitorId createCompetitor(FirstName firstName, LastName lastName, boolean isUnderage) {
        var newCompetitorId = competitorIdentityFactory.generateCompetitorId();
        var newCompetitor = Competitor.newCompetitor(newCompetitorId, firstName, lastName, isUnderage);

        competitorRepository.save(newCompetitor);
        domainEventPublisher.publishEvents(newCompetitor);
        return newCompetitorId;
    }

    public void approveByOwner(CompetitorId competitorId) {
        var approvedCompetitor = getCompetitor(competitorId)
                .approveByOwner();

        competitorRepository.save(approvedCompetitor);
        domainEventPublisher.publishEvents(approvedCompetitor);
    }

    public void approveByParent(CompetitorId competitorId) {
        var approvedCompetitor = getCompetitor(competitorId)
                .approveByParent();

        competitorRepository.save(approvedCompetitor);
        domainEventPublisher.publishEvents(approvedCompetitor);
    }
}
