package binarek.robio.ftl.app.execution;

import binarek.robio.ftl.api.execution.FtlCompetitionExecutionAppService;
import binarek.robio.ftl.api.execution.command.CreateFtlRunCommand;
import binarek.robio.ftl.api.execution.command.DeleteFtlRunCommand;
import binarek.robio.ftl.api.execution.model.FtlCompetitionResults;

import java.util.List;
import java.util.UUID;

class FtlCompetitionExecutionAppServiceImpl implements FtlCompetitionExecutionAppService {

    private final FtlCompetitionExecutionModelMapper modelMapper;

    FtlCompetitionExecutionAppServiceImpl(FtlCompetitionExecutionModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public void addRun(CreateFtlRunCommand command) {

    }

    @Override
    public void deleteRun(DeleteFtlRunCommand command) {

    }

    @Override
    public FtlCompetitionResults getResults(UUID competitionId) {
        return FtlCompetitionResults.builder()
                .results(List.of()) // TODO
                .build();
    }
}
