package binarek.robio.ftl;

import binarek.robio.ftl.command.ChangeRobotQualificationCommand;
import binarek.robio.ftl.command.RegisterRobotCommand;
import binarek.robio.ftl.exception.CompetitionNotFoundException;
import binarek.robio.ftl.exception.RobotAlreadyRegisteredException;
import binarek.robio.ftl.exception.RobotNotFoundException;
import binarek.robio.ftl.query.RobotByIdQuery;
import binarek.robio.ftl.query.RobotsByCompetitionIdQuery;
import binarek.robio.ftl.view.RobotView;

import java.util.Collection;

public interface RobotAppService {

    /**
     * Registers FTL robot.
     *
     * @param command register command
     * @throws CompetitionNotFoundException    if no competition found
     * @throws RobotAlreadyRegisteredException if robot with id has already been registered in competition
     */
    void registerRobot(RegisterRobotCommand command);

    /**
     * Qualifies FTL robot.
     * Qualified robots can start in FTL competitions.
     *
     * @param command qualify command
     * @throws RobotNotFoundException if robot with given id does not exist
     */
    void qualifyRobot(ChangeRobotQualificationCommand command);

    /**
     * Disqualifies FTL robot.
     * Disqualified robots cannot start nor continue participation in FTL competition.
     *
     * @param command disqualify command
     * @throws RobotNotFoundException if robot with given id does not exist
     */
    void disqualifyRobot(ChangeRobotQualificationCommand command);

    /**
     * Returns FTL robot for given robot id.
     *
     * @param query query
     * @return robot
     * @throws RobotNotFoundException if robot with given criteria does not exist
     */
    RobotView getRobot(RobotByIdQuery query);

    /**
     * Returns FTL robots.
     *
     * @param query query
     * @return robots
     */
    Collection<? extends RobotView> getRobots(RobotsByCompetitionIdQuery query);
}
