package binarek.robio.ftl;

import binarek.robio.ftl.command.ChangeCompetitionPlanRulesCommand;
import binarek.robio.ftl.command.InitializeCompetitionPlanCommand;
import binarek.robio.ftl.command.SearchCompetitionPlanCommand;
import binarek.robio.ftl.exception.CompetitionPlanAlreadyInitializedException;
import binarek.robio.ftl.exception.CompetitionPlanNotFoundException;
import binarek.robio.ftl.view.CompetitionPlanView;
import binarek.robio.ftl.view.RobotView;

import java.util.Collection;

public interface CompetitionPlanAppService {

    /**
     * Initializes new FTL plan for given competition id (only one plan per competition is allowed).
     *
     * @param command initialization command
     * @throws CompetitionPlanAlreadyInitializedException if plan for given competition id has been already initialized
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
     * Returns FTL plan for competition given id.
     *
     * @param command search command
     * @return competition plan
     * @throws CompetitionPlanNotFoundException if no plan found
     */
    CompetitionPlanView getPlan(SearchCompetitionPlanCommand command);

    /**
     * Returns robots registered in FTL plan.
     *
     * @param command plan search command
     * @return robots
     * @throws CompetitionPlanNotFoundException if no plan found
     */
    Collection<? extends RobotView> getPlanRobots(SearchCompetitionPlanCommand command);
}
