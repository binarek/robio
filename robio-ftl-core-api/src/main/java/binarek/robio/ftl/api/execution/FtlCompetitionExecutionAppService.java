package binarek.robio.ftl.api.execution;

import binarek.robio.ftl.api.execution.command.CreateFtlRunCommand;
import binarek.robio.ftl.api.execution.command.DeleteFtlRunCommand;
import binarek.robio.ftl.api.execution.model.FtlCompetitionResults;

import java.util.UUID;

public interface FtlCompetitionExecutionAppService {

    void addRun(CreateFtlRunCommand command);

    void deleteRun(DeleteFtlRunCommand command);

    FtlCompetitionResults getResults(UUID competitionId);
}
