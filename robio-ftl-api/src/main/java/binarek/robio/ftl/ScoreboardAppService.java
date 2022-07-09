package binarek.robio.ftl;

import binarek.robio.ftl.view.ScoreboardView;
import binarek.robio.shared.model.CompetitionId;

public interface ScoreboardAppService {

    ScoreboardView getScoreboard(CompetitionId competitionId);
}
