package binarek.robio.ftl;

import binarek.robio.ftl.command.RegisterRobotCommand;
import binarek.robio.ftl.command.SearchRobotCommand;
import binarek.robio.ftl.exception.RobotNotFoundException;
import binarek.robio.ftl.model.Robot;
import binarek.robio.ftl.view.RobotView;
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
    public RobotView getRobot(SearchRobotCommand command) {
        return robotRepository.getByRobotId(command.getRobotId())
                .orElseThrow(() -> RobotNotFoundException.of(command.getRobotId()));
    }
}
