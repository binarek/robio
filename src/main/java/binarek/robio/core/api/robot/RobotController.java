package binarek.robio.core.api.robot;

import binarek.robio.core.domain.robot.Robot;
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
                .orElseThrow();
    }

    @PostMapping
    public Robot createRobot(@RequestBody Robot robot) {
        return robotRepository.insert(robot);
    }
}
