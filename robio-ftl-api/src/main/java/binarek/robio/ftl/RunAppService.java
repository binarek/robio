package binarek.robio.ftl;

import binarek.robio.ftl.command.AddRunCommand;
import binarek.robio.ftl.command.EditRunResultCommand;
import binarek.robio.ftl.exception.CompetitionNotFoundException;
import binarek.robio.ftl.exception.RobotNotFoundException;
import binarek.robio.ftl.exception.RunAddValidationException;
import binarek.robio.ftl.exception.RunNotFoundException;
import binarek.robio.ftl.query.RunQuery;
import binarek.robio.ftl.view.RunView;

public interface RunAppService {

    /**
     * Adds new robot run.
     *
     * @param command add run command
     * @return number of run
     * @throws CompetitionNotFoundException if no competition found
     * @throws RobotNotFoundException       if no robot found
     * @throws RunAddValidationException    in case of validation error
     */
    Integer addRun(AddRunCommand command);

    /**
     * Edits existing run result (judgement and time)
     *
     * @param command edit run result command
     * @throws RunNotFoundException if no run found
     */
    void editRunResult(EditRunResultCommand command);

    /**
     * Returns FTL run.
     *
     * @param query query
     * @return run
     */
    RunView getRun(RunQuery query);
}
