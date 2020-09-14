package binarek.robio.registration.domain.competitor;

import org.springframework.stereotype.Service;

@Service
public final class CompetitorService {

    private final CompetitorRepository competitorRepository;

    public CompetitorService(CompetitorRepository competitorRepository) {
        this.competitorRepository = competitorRepository;
    }

    public void validateForCreation(Competitor competitor) {
        if (competitorRepository.existsByEmail(competitor.getEmail())) {
            throw CompetitorAlreadyExistsException.ofEmail(competitor.getEmail());
        }
    }
}
