package binarek.robio.ftl;

import binarek.robio.ftl.exception.CompetitionAlreadyExistsException;
import binarek.robio.ftl.exception.CompetitionStartValidationException;
import binarek.robio.ftl.model.CompetitionPlan;
import binarek.robio.ftl.validation.CompetitionStartValidation;
import binarek.robio.shared.model.CompetitionId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final RobotRepository robotRepository;

    public CompetitionService(CompetitionRepository competitionRepository, RobotRepository robotRepository) {
        this.competitionRepository = competitionRepository;
        this.robotRepository = robotRepository;
    }

    /**
     * Checks if can start competition from given competition plan.
     *
     * @param competitionPlan competition plan
     * @throws CompetitionAlreadyExistsException   if competition with given id is already started
     * @throws CompetitionStartValidationException if competition cannot start because of validation failure
     */
    public void checkIfCanStartCompetition(CompetitionPlan competitionPlan) {
        checkIfCompetitionAlreadyStarted(competitionPlan.getCompetitionId());
        checkIfCompetitionPlanIdEligibleToStart(competitionPlan);
    }

    private void checkIfCompetitionAlreadyStarted(CompetitionId competitionId) {
        if (competitionRepository.existsByCompetitionId(competitionId)) {
            throw CompetitionAlreadyExistsException.of(competitionId);
        }
    }

    private void checkIfCompetitionPlanIdEligibleToStart(CompetitionPlan competitionPlan) {
        final var validations = new ArrayList<CompetitionStartValidation>();

        validations.add(competitionPlan.checkCanStartCompetition());
        for (var robot : robotRepository.getByRobotId(competitionPlan.getRobots())) {
            validations.add(robot.checkCanStartInCompetition());
        }

        final var finalValidation = CompetitionStartValidation.mergeValidations(validations);
        if (!finalValidation.isSuccess()) {
            throw CompetitionStartValidationException.of(competitionPlan.getCompetitionId(), finalValidation);
        }
    }
}
