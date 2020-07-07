package binarek.robio.core.api.robot;

import binarek.robio.core.domain.robot.Robot;
import binarek.robio.core.domain.robot.RobotId;
import binarek.robio.core.domain.robot.RobotService;
import org.springframework.web.bind.annotation.*;

import static binarek.robio.common.api.ApiUtil.validateEntityPutRequest;

@RestController
@RequestMapping("/robots")
public class RobotController {

    private final RobotService robotService;

    public RobotController(RobotService robotService) {
        this.robotService = robotService;
    }

    @GetMapping("/{id}")
    public Robot getRobot(@PathVariable RobotId id) {
        return robotService.getRobot(id);
    }

    @PostMapping
    public Robot postRobot(@RequestBody Robot robot) {
        return robotService.createRobot(robot);
    }

    @PutMapping("/{id}")
    public Robot putRobot(@PathVariable RobotId id, @RequestBody Robot robot) {
        validateEntityPutRequest(id, robot.getId());
        return robotService.saveRobot(robot);
    }

    @DeleteMapping("/{id}")
    public void deleteRobot(@PathVariable RobotId id) {
        robotService.deleteRobot(id);
    }
}
