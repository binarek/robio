package binarek.robio.ftl;

import binarek.robio.ftl.command.ChangeCompetitionRulesCommand;
import binarek.robio.ftl.command.InitializeCompetitionCommand;
import binarek.robio.ftl.command.StartCompetitionCommand;
import binarek.robio.ftl.exception.CompetitionAlreadyInitializedException;
import binarek.robio.ftl.exception.CompetitionAlreadyStartedException;
import binarek.robio.ftl.exception.CompetitionNotFoundException;
import binarek.robio.ftl.exception.CompetitionStartValidationException;
import binarek.robio.ftl.query.CompetitionByIdQuery;
import binarek.robio.ftl.view.CompetitionView;

public interface CompetitionAppService {

    /**
     * Initializes new FTL competition.
     *
     * @param command initialization command
     * @throws CompetitionAlreadyInitializedException if competition for given competition id has been already initialized
     */
    void initializeCompetition(InitializeCompetitionCommand command);

    /**
     * Starts FTL competition.
     *
     * @param command start competition command
     * @throws CompetitionNotFoundException        if no competition found
     * @throws CompetitionAlreadyStartedException  if competition is already started
     * @throws CompetitionStartValidationException if competition cannot start because of validation error
     */
    void startCompetition(StartCompetitionCommand command);

    /**
     * Changes rules in existing FTL competition.
     * If provided rules are null then default ones will be applied.
     *
     * @param command change rules command
     * @throws CompetitionNotFoundException if no competition found
     */
    void changeCompetitionRules(ChangeCompetitionRulesCommand command);

    /**
     * Returns FTL competition for given id.
     *
     * @param query query
     * @return competition
     * @throws CompetitionNotFoundException if no competition found
     */
    CompetitionView getCompetition(CompetitionByIdQuery query);
}
