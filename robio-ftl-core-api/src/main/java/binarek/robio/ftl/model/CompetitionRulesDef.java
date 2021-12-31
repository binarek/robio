package binarek.robio.ftl.model;

import binarek.robio.util.codegen.ValueDefStyle;
import org.apache.commons.lang3.Validate;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
@ValueDefStyle
interface CompetitionRulesDef {

    int MIN_ROBOTS_TO_START_COMPETITION = 2;

    @Nullable
    Integer getRunsLimitPerRobot();

    @Value.Default
    default Integer getMinRobotsToStartCompetition() {
        return MIN_ROBOTS_TO_START_COMPETITION;
    }

    @Value.Check
    default void validate() {
        Validate.isTrue(getMinRobotsToStartCompetition() >= MIN_ROBOTS_TO_START_COMPETITION,
                "Minimum number of robots to start competition cannot be less then " + MIN_ROBOTS_TO_START_COMPETITION);
    }
}
