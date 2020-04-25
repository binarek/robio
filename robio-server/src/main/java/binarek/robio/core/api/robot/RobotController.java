package binarek.robio.core.api.robot;

import binarek.robio.core.domain.robot.ImmutableRobot;
import binarek.robio.core.domain.robot.Robot;
import binarek.robio.core.domain.robot.RobotNotExistException;
import binarek.robio.core.domain.robot.RobotRepository;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/robots")
public class RobotController {

    private final RobotRepository robotRepository;

    public RobotController(RobotRepository robotRepository) {
        this.robotRepository = robotRepository;
    }

    @GetMapping("/{id}")
    public Robot getRobot(@PathVariable UUID id) {
        return robotRepository.getById(id)
                .orElseThrow(() -> new RobotNotExistException(id));
    }

    @PostMapping
    public Robot postRobot(@RequestBody Robot robot) {
        return robotRepository.insert(robot);
    }

    @PutMapping("/{id}")
    public Robot putRobot(@PathVariable UUID id, @RequestBody ImmutableRobot robot) {
        return robotRepository.insertOrUpdate(robot.withId(id));
    }

    @DeleteMapping("/{id}")
    public void deleteRobot(@PathVariable UUID id) {
        if (!robotRepository.delete(id)) {
            throw new RobotNotExistException(id);
        }
    }
}
