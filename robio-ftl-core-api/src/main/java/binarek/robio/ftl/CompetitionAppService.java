package binarek.robio.ftl;

import binarek.robio.ftl.command.SearchCompetitionCommand;
import binarek.robio.ftl.command.StartCompetitionCommand;
import binarek.robio.ftl.exception.CompetitionNotFoundException;
import binarek.robio.ftl.exception.CompetitionPlanNotFoundException;
import binarek.robio.ftl.exception.CompetitionStartValidationException;
import binarek.robio.ftl.view.CompetitionView;
import binarek.robio.ftl.view.RobotView;

import java.util.Collection;

public interface CompetitionAppService {

    /**
     * Starts FTL competition using plan associated with given competition id.
     *
     * @param command start competition command
     * @throws CompetitionPlanNotFoundException    if no plan for competition id exits
     * @throws CompetitionStartValidationException if competition cannot start because of validation failure
     */
    void startCompetition(StartCompetitionCommand command);

    /**
     * Returns FTL competition for given id.
     *
     * @param command search command
     * @return competition
     * @throws CompetitionNotFoundException if no competition found
     */
    CompetitionView getCompetition(SearchCompetitionCommand command);

    /**
     * Returns robots started in FTL competition.
     *
     * @param command competition search command
     * @return robots
     * @throws CompetitionPlanNotFoundException if no competition found
     */
    Collection<? extends RobotView> getCompetitionRobots(SearchCompetitionCommand command);
}
