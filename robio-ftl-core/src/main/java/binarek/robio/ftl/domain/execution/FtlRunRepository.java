package binarek.robio.ftl.domain.execution;

import binarek.robio.ftl.domain.execution.model.FtlParticipation;

public interface FtlRunRepository {

    int countRunsForParticipation(FtlParticipation participation);

    
}
