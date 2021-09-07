package binarek.robio.ftl;

import binarek.robio.ftl.command.ChangeRobotQualificationCommand;
import binarek.robio.ftl.command.RegisterRobotCommand;
import binarek.robio.ftl.command.SearchRobotCommand;
import binarek.robio.ftl.exception.RobotNotFoundException;
import binarek.robio.ftl.model.Robot;
import binarek.robio.ftl.view.RobotView;
import binarek.robio.shared.exception.EntityHasChangedException;
import binarek.robio.shared.model.RobotId;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class RobotAppServiceImpl implements RobotAppService {

    private final RobotRepository robotRepository;
    private final RobotService robotService;

    RobotAppServiceImpl(RobotRepository robotRepository, RobotService robotService) {
        this.robotRepository = robotRepository;
        this.robotService = robotService;
    }

    @Override
    @Transactional
    public void registerRobot(RegisterRobotCommand command) {
        robotService.validateIfCanRegisterRobot(command.getRobotId());
        final var robot = Robot.newRobot(command.getRobotId(), command.getName());
        robotRepository.save(robot);
    }

    @Override
    @Retryable(EntityHasChangedException.class)
    public void qualifyRobot(ChangeRobotQualificationCommand command) {
        final var robot = getRobot(command.getRobotId());
        robotRepository.save(robot.qualify());
    }

    @Override
    @Retryable(EntityHasChangedException.class)
    public void disqualifyRobot(ChangeRobotQualificationCommand command) {
        final var robot = getRobot(command.getRobotId());
        robotRepository.save(robot.disqualify());
    }

    @Override
    public RobotView getRobot(SearchRobotCommand command) {
        return getRobot(command.getRobotId());
    }

    private Robot getRobot(RobotId robotId) {
        return robotRepository.getByRobotId(robotId)
                .orElseThrow(() -> RobotNotFoundException.of(robotId));
    }
}
