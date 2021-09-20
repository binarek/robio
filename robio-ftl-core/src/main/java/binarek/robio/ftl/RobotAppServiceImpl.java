package binarek.robio.ftl;

import binarek.robio.ftl.command.ChangeRobotQualificationCommand;
import binarek.robio.ftl.command.RegisterRobotCommand;
import binarek.robio.ftl.command.SearchRobotCommand;
import binarek.robio.ftl.command.SearchRobotsCommand;
import binarek.robio.ftl.exception.RobotNotFoundException;
import binarek.robio.ftl.model.Robot;
import binarek.robio.ftl.view.RobotView;
import binarek.robio.shared.exception.EntityHasChangedException;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

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
        final var competitionId = command.getCompetitionId();
        final var robotId = command.getRobotId();

        robotService.validateIfCanRegisterRobot(competitionId, robotId);
        final var robot = Robot.registerRobot(competitionId, robotId, command.getName());
        robotRepository.save(robot);
    }

    @Override
    @Retryable(EntityHasChangedException.class)
    public void qualifyRobot(ChangeRobotQualificationCommand command) {
        final var robot = getRobot(command.getCompetitionId(), command.getRobotId())
                .qualify();
        robotRepository.save(robot);
    }

    @Override
    @Retryable(EntityHasChangedException.class)
    public void disqualifyRobot(ChangeRobotQualificationCommand command) {
        final var robot = getRobot(command.getCompetitionId(), command.getRobotId())
                .disqualify();
        robotRepository.save(robot);
    }

    @Override
    public RobotView getRobot(SearchRobotCommand command) {
        return getRobot(command.getCompetitionId(), command.getRobotId());
    }

    @Override
    public Collection<? extends RobotView> getRobots(SearchRobotsCommand command) {
        return robotRepository.getByCompetitionId(command.getCompetitionId());
    }

    private Robot getRobot(CompetitionId competitionId, RobotId robotId) {
        return robotRepository.getByCompetitionIdAndRobotId(competitionId, robotId)
                .orElseThrow(() -> RobotNotFoundException.of(competitionId, robotId));
    }
}
