package binarek.robio.ftl;

import binarek.robio.ftl.command.ChangeRobotQualificationCommand;
import binarek.robio.ftl.command.RegisterRobotCommand;
import binarek.robio.ftl.command.SearchRobotCommand;
import binarek.robio.ftl.exception.RobotAlreadyExistsException;
import binarek.robio.ftl.exception.RobotNotFoundException;
import binarek.robio.ftl.view.RobotView;

public interface RobotAppService {

    /**
     * Registers FTL robot.
     *
     * @param command register command
     * @throws RobotAlreadyExistsException if robot with given id exists
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
     * @param command search command
     * @return robot
     * @throws RobotNotFoundException if robot with given criteria does not exist
     */
    RobotView getRobot(SearchRobotCommand command);
}
