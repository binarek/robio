package binarek.robio.ftl.planning;

import binarek.robio.ftl.planning.command.InitializeCompetitionPlanCommand;
import binarek.robio.ftl.planning.command.SearchCompetitionPlanCommand;
import binarek.robio.ftl.planning.exception.CompetitionPlanAlreadyExistsException;
import binarek.robio.ftl.planning.exception.CompetitionPlanNotFoundException;
import binarek.robio.ftl.planning.view.CompetitionPlanView;

public interface CompetitionPlanAppService {

    /**
     * Initializes new FTL plan for given competition id (only one plan per competition is allowed)
     *
     * @param command initialization command
     * @throws CompetitionPlanAlreadyExistsException if plan for given competition id has been already initialized
     */
    void initializePlan(InitializeCompetitionPlanCommand command);

    /**
     * Returns FTL plan for competition given id
     *
     * @param command search command
     * @return competition plan
     * @throws CompetitionPlanNotFoundException if no plan found
     */
    CompetitionPlanView getPlan(SearchCompetitionPlanCommand command);
}
