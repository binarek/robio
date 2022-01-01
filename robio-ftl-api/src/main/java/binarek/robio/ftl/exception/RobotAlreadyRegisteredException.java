package binarek.robio.ftl.exception;

import binarek.robio.shared.exception.BusinessException;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;

public class RobotAlreadyRegisteredException extends BusinessException {

    private RobotAlreadyRegisteredException(String message) {
        super(message);
    }

    public static RobotAlreadyRegisteredException of(CompetitionId competitionId, RobotId robotId) {
        return new RobotAlreadyRegisteredException("Robot with id " + robotId + " already exists in competition with id " + competitionId);
    }
}
