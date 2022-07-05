package binarek.robio.ftl;

import binarek.robio.ftl.exception.CompetitionAlreadyInitializedException;
import binarek.robio.ftl.exception.CompetitionAlreadyStartedException;
import binarek.robio.ftl.exception.CompetitionStartValidationException;
import binarek.robio.ftl.model.Competition;
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

    public CompetitionService(CompetitionRepository competitionRepository,
                              RobotRepository robotRepository) {
        this.competitionRepository = competitionRepository;
        this.robotRepository = robotRepository;
    }

    /**
     * Validates if can initialize competition.
     *
     * @param competitionId competition id
     * @throws CompetitionAlreadyInitializedException if competition is already initialized
     */
    public void validateIfCanInitializeCompetition(CompetitionId competitionId) {
        if (competitionRepository.existsByCompetitionId(competitionId)) {
            throw CompetitionAlreadyInitializedException.of(competitionId);
        }
    }

    /**
     * Validates if can start competition.
     *
     * @param competition competition to start
     * @throws CompetitionAlreadyStartedException  if competition is already started
     * @throws CompetitionStartValidationException if competition cannot start because of validation error
     */
    public void validateIfCanStartCompetition(Competition competition) {
        validateIfCompetitionAlreadyStarted(competition);
        validateIfCompetitionIsEligibleToStart(competition);
    }

    private void validateIfCompetitionAlreadyStarted(Competition competition) {
        if (competition.wasStarted()) {
            throw CompetitionAlreadyStartedException.of(competition.getCompetitionId());
        }
    }

    private void validateIfCompetitionIsEligibleToStart(Competition competition) {
        final var validations = new HashSet<CompetitionStartValidation>();
        final var robots = robotRepository.getByCompetitionId(competition.getCompetitionId());

        addCanStartCompetitionValidations(validations, competition, robots);
        addRobotsValidations(validations, robots);

        final var finalValidation = CompetitionStartValidation.mergeValidations(validations);
        if (!finalValidation.isSuccess()) {
            throw CompetitionStartValidationException.of(competition.getCompetitionId(), finalValidation);
        }
    }

    private void addCanStartCompetitionValidations(Set<CompetitionStartValidation> validations, Competition competition, Collection<Robot> robots) {
        validations.add(canStartCompetitionValidation(competition, robots));
    }

    private void addRobotsValidations(Set<CompetitionStartValidation> validations, Collection<Robot> robots) {
        robots.stream()
                .map(Robot::checkIsReadyToStartCompetition)
                .forEach(validations::add);
    }

    private CompetitionStartValidation canStartCompetitionValidation(Competition competition, Collection<Robot> robots) {
        final var minRobotsToStartCompetition = competition.getRules().getMinRobotsToStartCompetition();
        final var robotsNumber = robots.stream().filter(Robot::participatesInCompetition).count();

        if (robotsNumber < minRobotsToStartCompetition) {
            return CompetitionStartValidation.error(NotEnoughRobotsToStartCompetitionValidationError.of(minRobotsToStartCompetition, robotsNumber));
        } else {
            return CompetitionStartValidation.success();
        }
    }
}
