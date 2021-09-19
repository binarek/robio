package binarek.robio.ftl;

import binarek.robio.ftl.exception.CompetitionNotFoundException;
import binarek.robio.ftl.exception.RobotAlreadyRegisteredException;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;
import org.springframework.stereotype.Service;

@Service
public class RobotService {

    private final RobotRepository robotRepository;
    private final CompetitionRepository competitionRepository;

    RobotService(RobotRepository robotRepository,
                 CompetitionRepository competitionRepository) {
        this.robotRepository = robotRepository;
        this.competitionRepository = competitionRepository;
    }

    /**
     * Checks if new robot with given id can be registered in competition.
     *
     * @param competitionId competition id
     * @param robotId       robot id
     * @throws CompetitionNotFoundException    if competition with given id not found
     * @throws RobotAlreadyRegisteredException if robot with id has already been registered in competition
     */
    public void validateIfCanRegisterRobot(CompetitionId competitionId, RobotId robotId) {
        if (!competitionRepository.existsByCompetitionId(competitionId)) {
            throw CompetitionNotFoundException.of(competitionId);
        }
        if (robotRepository.existsByCompetitionIdAndRobotId(competitionId, robotId)) {
            throw RobotAlreadyRegisteredException.of(competitionId, robotId);
        }
    }
}
