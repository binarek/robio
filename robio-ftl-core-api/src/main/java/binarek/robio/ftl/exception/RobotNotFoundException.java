package binarek.robio.ftl.exception;

import binarek.robio.shared.exception.BusinessException;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;

public class RobotNotFoundException extends BusinessException {

    private RobotNotFoundException(String message) {
        super(message);
    }

    public static RobotNotFoundException of(CompetitionId competitionId, RobotId robotId) {
        return new RobotNotFoundException("Cannot find robot with id " + robotId + " in competition with id " + competitionId);
    }
}
