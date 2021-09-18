package binarek.robio.ftl.adapter.persistence;

import binarek.robio.ftl.model.CompetitionRules;
import org.jooq.JSON;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
class CompetitionRulesJsonTypeMapper {

    private final FtlJsonTypeMapper<CompetitionRules> competitionRulesTypeMapper = FtlJsonTypeMapper.ofType(CompetitionRules.class);

    @Nullable
    CompetitionRules toCompetitionRules(@Nullable JSON rulesJson) {
        return competitionRulesTypeMapper.toObject(rulesJson);
    }

    @Nullable
    JSON toCompetitionRules(@Nullable CompetitionRules rules) {
        return competitionRulesTypeMapper.toJson(rules);
    }
}
