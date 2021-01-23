package binarek.robio.ftl.planning;

import binarek.robio.ftl.planning.exception.CompetitionPlanAlreadyExistsException;
import binarek.robio.shared.model.CompetitionId;
import org.springframework.stereotype.Service;

@Service
public class CompetitionPlanService {

    private final CompetitionPlanRepository competitionPlanRepository;

    public CompetitionPlanService(CompetitionPlanRepository competitionPlanRepository) {
        this.competitionPlanRepository = competitionPlanRepository;
    }

    /**
     * Checks if new plan for given competition id can be initialized
     *
     * @param competitionId competition id
     * @throws CompetitionPlanAlreadyExistsException if plan for given competition id has already been initialized
     */
    public void validateIfCanInitializeCompetitionPlan(CompetitionId competitionId) {
        if (competitionPlanRepository.existsByCompetitionId(competitionId)) {
            throw CompetitionPlanAlreadyExistsException.of(competitionId);
        }
    }
}
