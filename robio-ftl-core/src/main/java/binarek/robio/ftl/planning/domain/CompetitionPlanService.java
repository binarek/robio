package binarek.robio.ftl.planning.domain;

import binarek.robio.ftl.planning.api.exception.CompetitionPlanAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
    public void validateIfCanInitializeCompetitionPlan(UUID competitionId) {
        if (competitionPlanRepository.existsByCompetitionId(competitionId)) {
            throw CompetitionPlanAlreadyExistsException.ofCompetitionId(competitionId);
        }
    }
}
