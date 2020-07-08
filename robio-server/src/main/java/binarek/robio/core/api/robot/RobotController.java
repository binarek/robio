package binarek.robio.core.api.robot;

import binarek.robio.common.api.BadRequestException;
import binarek.robio.core.domain.robot.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static binarek.robio.common.api.ApiUtil.*;

@RestController
@RequestMapping("/robots")
public class RobotController {

    private final RobotService robotService;

    public RobotController(RobotService robotService) {
        this.robotService = robotService;
    }

    @GetMapping
    public List<? extends Robot> getRobots(@RequestParam(defaultValue = DEFAULT_LIMIT) int limit,
                                           @RequestParam(defaultValue = DEFAULT_OFFSET) int offset,
                                           @RequestParam(defaultValue = "name") List<String> sort) {
        return robotService.getRobots(buildRobotFetchProperties(limit, offset, sort));
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

    private static RobotFetchProperties buildRobotFetchProperties(int limit, int offset, List<String> sort) {
        try {
            return RobotFetchProperties.builder()
                    .limit(limit)
                    .offset(offset)
                    .sort(sort.stream().map(RobotSortableField::fromFieldName).collect(Collectors.toUnmodifiableList()))
                    .build();
        } catch (IllegalStateException | IllegalArgumentException e) {
            throw new BadRequestException(e.getLocalizedMessage());
        }
    }
}
