package binarek.robio.ftl;

import binarek.robio.ftl.exception.CompetitionNotFoundException;
import binarek.robio.ftl.exception.RobotNotFoundException;
import binarek.robio.ftl.exception.RunAddValidationException;
import binarek.robio.ftl.model.Competition;
import binarek.robio.ftl.model.Robot;
import binarek.robio.ftl.model.Run;
import binarek.robio.ftl.validation.RunAddValidation;
import binarek.robio.ftl.validation.RunsLimitExceededValidationError;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RunService {

    private final RunRepository runRepository;
    private final RobotRepository robotRepository;
    private final CompetitionRepository competitionRepository;

    RunService(RunRepository runRepository,
               RobotRepository robotRepository,
               CompetitionRepository competitionRepository) {
        this.runRepository = runRepository;
        this.robotRepository = robotRepository;
        this.competitionRepository = competitionRepository;
    }

    /**
     * Returns last robot run.
     *
     * @param competitionId competition id
     * @param robotId       robot id
     * @return run
     */
    public Optional<Run> getLastRun(CompetitionId competitionId, RobotId robotId) {
        return runRepository.getByCompetitionIdAndRobotIdAndMaxNumber(competitionId, robotId);
    }

    /**
     * Validates if new run can be added.
     *
     * @param competitionId competition id
     * @param robotId       robot id
     * @throws CompetitionNotFoundException if no competition found
     * @throws RobotNotFoundException       if no robot found
     * @throws RunAddValidationException    if validation error occurred
     */
    public void validateIfCanAddRun(CompetitionId competitionId, RobotId robotId) {
        final var competition = getCompetition(competitionId);
        final var robot = getRobot(competitionId, robotId);

        final var finalValidation = RunAddValidation.mergeValidations(
                competition.checkCanAddRun(),
                robot.checkCanAddRun(),
                checkIfAddRunMatchesRules(competition, robot));

        if (!finalValidation.isSuccess()) {
            throw RunAddValidationException.of(competitionId, robotId, finalValidation);
        }
    }

    private RunAddValidation checkIfAddRunMatchesRules(Competition competition, Robot robot) {
        final var runLimitPerRobot = competition.getRules().getRunsLimitPerRobot();
        if (runLimitPerRobot != null) {
            final var robotRunsNumber = runRepository.countByCompetitionIdAndRobotId(robot.getCompetitionId(), robot.getRobotId());

            if (robotRunsNumber >= runLimitPerRobot) {
                return RunAddValidation.error(RunsLimitExceededValidationError.of(robotRunsNumber));
            }
        }
        return RunAddValidation.success();
    }

    private Competition getCompetition(CompetitionId competitionId) {
        return competitionRepository.getByCompetitionId(competitionId)
                .orElseThrow(() -> CompetitionNotFoundException.of(competitionId));
    }

    private Robot getRobot(CompetitionId competitionId, RobotId robotId) {
        return robotRepository.getByCompetitionIdAndRobotId(competitionId, robotId)
                .orElseThrow(() -> RobotNotFoundException.of(competitionId, robotId));
    }
}
