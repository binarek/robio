package binarek.robio.ftl.fixture

import binarek.robio.ftl.model.Competition
import binarek.robio.ftl.model.CompetitionRules
import binarek.robio.ftl.model.CompetitionState
import binarek.robio.ftl.model.ImmutableCompetition
import binarek.robio.shared.model.BusinessDateTime
import binarek.robio.shared.model.CompetitionId
import org.springframework.lang.Nullable

class CompetitionFixture {

    private CompetitionId competitionId
    @Nullable
    private Long version
    private CompetitionRules rules
    private BusinessDateTime initializeDateTime

    static CompetitionFixture competition(SampleData.CompetitionData competitionData) {
        return new CompetitionFixture(
                competitionId: competitionData.competitionId,
                rules: competitionData.rules,
                initializeDateTime: competitionData.initializeDate
        )
    }

    Competition build() {
        return ImmutableCompetition.builder()
                .competitionId(competitionId)
                .version(version)
                .rules(rules)
                .state(CompetitionState.INITIALIZED)
                .initializeDateTime(initializeDateTime)
                .build()
    }
}
