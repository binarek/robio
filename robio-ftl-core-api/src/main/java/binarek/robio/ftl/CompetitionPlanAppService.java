package binarek.robio.ftl;

import binarek.robio.ftl.command.*;
import binarek.robio.ftl.exception.CompetitionPlanAlreadyExistsException;
import binarek.robio.ftl.exception.CompetitionPlanNotFoundException;
import binarek.robio.ftl.view.CompetitionPlanView;

public interface CompetitionPlanAppService {

    /**
     * Initializes new FTL plan for given competition id (only one plan per competition is allowed).
     *
     * @param command initialization command
     * @throws CompetitionPlanAlreadyExistsException if plan for given competition id has been already initialized
     */
    void initializePlan(InitializeCompetitionPlanCommand command);

    /**
     * Changes rules in existing competition plan.
     * If provided rules are null then default ones will be applied.
     *
     * @param command change rules command
     * @throws CompetitionPlanNotFoundException if no plan found
     */
    void changePlanRules(ChangeCompetitionPlanRulesCommand command);

    /**
     * Adds robots to existing competition plan.
     * Method does not check robot state (if exists, can participate in competition etc.) - it is considered that such
     * check is made when competition starts.
     *
     * @param command add robots command
     * @throws CompetitionPlanNotFoundException if no plan found
     */
    void addRobots(AddRobotsToCompetitionPlanCommand command);

    /**
     * Removes robots from existing competition plan.
     * Method does not make any checks. This is reversal operation for {@link #addRobots} method.
     *
     * @param command remove robots command
     * @throws CompetitionPlanNotFoundException if no plan found
     */
    void removeRobots(RemoveRobotsFromCompetitionPlanCommand command);

    /**
     * Returns FTL plan for competition given id.
     *
     * @param command search command
     * @return competition plan
     * @throws CompetitionPlanNotFoundException if no plan found
     */
    CompetitionPlanView getPlan(SearchCompetitionPlanCommand command);
}
