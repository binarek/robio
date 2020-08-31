package binarek.robio.registration.rest.competitor;

import binarek.robio.common.codegen.BaseMapperConfig;
import binarek.robio.registration.domain.competitor.Competitor;
import binarek.robio.registration.domain.competitor.CompetitorApprovals;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public interface CompetitorDtoMapper {

    @Mapping(target = "isUnderage", source = "underage")
    @Mapping(target = "isActive", source = "active")
    CompetitorDto toCompetitorDto(Competitor competitor);

    @Mapping(target = "isApprovedByOwner", source = "approvedByOwner")
    @Mapping(target = "isApprovedByParent", source = "approvedByParent")
    CompetitorApprovalsDto toCompetitorApprovalsDto(CompetitorApprovals competitorApprovals);
}
