package binarek.robio.ftl;

import binarek.robio.ftl.exception.CompetitionAlreadyStartedException;
import binarek.robio.ftl.exception.CompetitionStartValidationException;
import binarek.robio.ftl.model.CompetitionPlan;
import binarek.robio.ftl.model.Robot;
import binarek.robio.ftl.validation.CompetitionStartValidation;
import binarek.robio.ftl.validation.NotEnoughRobotsToStartCompetitionValidationError;
import binarek.robio.shared.model.CompetitionId;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
     * @param competitionPlan competition plan to start
     * @throws CompetitionAlreadyStartedException  if competition with given id is already started
     * @throws CompetitionStartValidationException if competition cannot start because of validation error
     */
    public void checkIfCanStartCompetition(CompetitionPlan competitionPlan) {
        checkIfCompetitionAlreadyStarted(competitionPlan.getCompetitionId());
        checkIfCompetitionPlanIsEligibleToStart(competitionPlan);
    }

    private void checkIfCompetitionAlreadyStarted(CompetitionId competitionId) {
        if (competitionRepository.existsByCompetitionId(competitionId)) {
            throw CompetitionAlreadyStartedException.of(competitionId);
        }
    }

    private void checkIfCompetitionPlanIsEligibleToStart(CompetitionPlan competitionPlan) {
        final var validations = new HashSet<CompetitionStartValidation>();
        final var robots = robotRepository.getByCompetitionId(competitionPlan.getCompetitionId());

        addCanStartPlanValidations(validations, competitionPlan, robots);
        addRobotsValidations(validations, robots);

        final var finalValidation = CompetitionStartValidation.mergeValidations(validations);
        if (!finalValidation.isSuccess()) {
            throw CompetitionStartValidationException.of(competitionPlan.getCompetitionId(), finalValidation);
        }
    }

    private void addCanStartPlanValidations(Set<CompetitionStartValidation> validations, CompetitionPlan competitionPlan, Collection<Robot> robots) {
        validations.add(canStartPlanValidation(competitionPlan, robots));
    }

    private void addRobotsValidations(Set<CompetitionStartValidation> validations, Collection<Robot> robots) {
        robots.stream()
                .map(Robot::checkIsReadyToStartCompetition)
                .forEach(validations::add);
    }

    private CompetitionStartValidation canStartPlanValidation(CompetitionPlan competitionPlan, Collection<Robot> robots) {
        final var minRobotsToStartCompetition = competitionPlan.getRules().getMinRobotsToStartCompetition();
        final var robotsNumber = robots.stream().filter(Robot::canParticipateInCompetition).count();

        if (robotsNumber < minRobotsToStartCompetition) {
            return CompetitionStartValidation.error(NotEnoughRobotsToStartCompetitionValidationError.of(minRobotsToStartCompetition, robotsNumber));
        } else {
            return CompetitionStartValidation.success();
        }
    }
}
