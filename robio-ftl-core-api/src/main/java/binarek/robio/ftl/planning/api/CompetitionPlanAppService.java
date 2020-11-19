package binarek.robio.ftl.planning.api;

import binarek.robio.ftl.planning.api.command.InitializeCompetitionPlanCommand;
import binarek.robio.ftl.planning.api.exception.CompetitionPlanAlreadyExistsException;
import binarek.robio.ftl.planning.api.exception.CompetitionPlanNotFoundException;
import binarek.robio.ftl.planning.api.query.CompetitionPlanView;

import java.util.UUID;

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
     * @param competitionId competition id
     * @return competition plan
     * @throws CompetitionPlanNotFoundException if no plan found
     */
    CompetitionPlanView getPlan(UUID competitionId);
}
