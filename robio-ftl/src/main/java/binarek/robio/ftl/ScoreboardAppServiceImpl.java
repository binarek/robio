package binarek.robio.ftl;

import binarek.robio.ftl.view.ScoreboardView;
import binarek.robio.shared.model.CompetitionId;
import org.springframework.stereotype.Service;

@Service
class ScoreboardAppServiceImpl implements ScoreboardAppService {

    private final ScoreboardService scoreboardService;

    ScoreboardAppServiceImpl(ScoreboardService scoreboardService) {
        this.scoreboardService = scoreboardService;
    }

    @Override
    public ScoreboardView getScoreboard(CompetitionId competitionId) {
        return scoreboardService.getScoreboard(competitionId);
    }
}
