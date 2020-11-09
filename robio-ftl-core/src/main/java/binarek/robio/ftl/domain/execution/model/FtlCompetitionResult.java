package binarek.robio.ftl.domain.execution.model;

import org.springframework.lang.Nullable;

public abstract class FtlCompetitionResult {

    public abstract FtlParticipation participation();

    public abstract Integer place();

    @Nullable
    public abstract Time bestTime();
}
