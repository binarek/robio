package binarek.robio.ftl.domain.planning;

import binarek.robio.common.core.shared.BusinessException;

import java.util.UUID;

public class FtlCompetitionPlanNotReadyException extends BusinessException {

    public FtlCompetitionPlanNotReadyException(UUID competitionId) {
        super("Ftl competition plan for id " + competitionId + " is not ready");
    }
}
