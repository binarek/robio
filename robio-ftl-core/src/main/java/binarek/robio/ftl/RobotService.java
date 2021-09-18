package binarek.robio.ftl;

import binarek.robio.ftl.exception.CompetitionAlreadyStartedException;
import binarek.robio.ftl.exception.CompetitionPlanNotFoundException;
import binarek.robio.ftl.exception.RobotAlreadyRegisteredException;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;
import org.springframework.stereotype.Service;

@Service
public class RobotService {

    private final RobotRepository robotRepository;
    private final CompetitionPlanRepository competitionPlanRepository;
    private final CompetitionRepository competitionRepository;

    RobotService(RobotRepository robotRepository,
                 CompetitionPlanRepository competitionPlanRepository,
                 CompetitionRepository competitionRepository) {
        this.robotRepository = robotRepository;
        this.competitionPlanRepository = competitionPlanRepository;
        this.competitionRepository = competitionRepository;
    }

    /**
     * Checks if new robot with given id can be registered in competition.
     *
     * @param competitionId competition id
     * @param robotId       robot id
     * @throws CompetitionPlanNotFoundException   if competition plan for given id not found
     * @throws CompetitionAlreadyStartedException if competition already started
     * @throws RobotAlreadyRegisteredException    if robot with id has already been registered in competition
     */
    public void validateIfCanRegisterRobot(CompetitionId competitionId, RobotId robotId) {
        if (!competitionPlanRepository.existsByCompetitionId(competitionId)) {
            throw CompetitionPlanNotFoundException.of(competitionId);
        }
        if (competitionRepository.existsByCompetitionId(competitionId)) {
            throw CompetitionAlreadyStartedException.of(competitionId);
        }
        if (robotRepository.existsByCompetitionIdAndRobotId(competitionId, robotId)) {
            throw RobotAlreadyRegisteredException.of(competitionId, robotId);
        }
    }
}
