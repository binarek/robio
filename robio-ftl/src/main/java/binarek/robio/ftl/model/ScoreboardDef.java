package binarek.robio.ftl.model;

import binarek.robio.ftl.view.ScoreboardView;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable(builder = false)
@ValueDefStyle
abstract class ScoreboardDef implements ScoreboardView {

    @Value.Parameter
    public abstract List<Score> getScores();

    public static Scoreboard empty() {
        return Scoreboard.of(List.of());
    }
}
