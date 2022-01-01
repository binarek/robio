package binarek.robio.ftl.exception;

import binarek.robio.shared.exception.BusinessException;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;

public class RunNotFoundException extends BusinessException {

    private RunNotFoundException(String message) {
        super(message);
    }

    public static RunNotFoundException of(CompetitionId competitionId, RobotId robotId, Integer number) {
        return new RunNotFoundException("Cannot find run with competition id " + competitionId + ", robot id " + robotId + ", number " + number);
    }
}
