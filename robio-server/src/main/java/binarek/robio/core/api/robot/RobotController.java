package binarek.robio.core.api.robot;

import binarek.robio.core.domain.robot.Robot;
import binarek.robio.core.domain.robot.RobotService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static binarek.robio.common.api.ApiUtil.validateDomainEntityPutRequest;

@RestController
@RequestMapping("/robots")
public class RobotController {

    private final RobotService robotService;

    public RobotController(RobotService robotService) {
        this.robotService = robotService;
    }

    @GetMapping("/{id}")
    public Robot getRobot(@PathVariable UUID id) {
        return robotService.getRobot(id);
    }

    @PostMapping
    public Robot postRobot(@RequestBody Robot robot) {
        return robotService.createRobot(robot);
    }

    @PutMapping("/{id}")
    public Robot putRobot(@PathVariable UUID id, @RequestBody Robot robot) {
        validateDomainEntityPutRequest(id, robot);
        return robotService.saveRobot(robot);
    }

    @DeleteMapping("/{id}")
    public void deleteRobot(@PathVariable UUID id) {
        robotService.deleteRobot(id);
    }
}
