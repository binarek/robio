package binarek.robio.ftl.model;

import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@ValueDefStyle
abstract class ScoreboardDef {

    @Value.Parameter
    abstract List<Score> getScores();

    public static Scoreboard empty() {
        return Scoreboard.of(List.of());
    }
}
