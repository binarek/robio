package binarek.robio.registration.rest.competitor;

import binarek.robio.registration.app.CompetitorAppService;
import binarek.robio.registration.domain.competitor.CompetitorId;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration/competitors")
public class CompetitorController {

    private final CompetitorAppService competitorAppService;
    private final CompetitorDtoMapper competitorDtoMapper;

    public CompetitorController(CompetitorAppService competitorAppService, CompetitorDtoMapper competitorDtoMapper) {
        this.competitorAppService = competitorAppService;
        this.competitorDtoMapper = competitorDtoMapper;
    }

    @PostMapping
    public CompetitorDto createCompetitor(@RequestBody CompetitorDto competitor) {
        var competitorId = competitorAppService.createCompetitor(
                competitor.getFirstName(),
                competitor.getLastName(),
                BooleanUtils.isTrue(competitor.getIsUnderage()));
        return competitorDtoMapper.toCompetitorDto(competitorAppService.getCompetitor(competitorId));
    }

    @PostMapping("/{id}/approvals/owner")
    public void approveByOwner(@PathVariable CompetitorId id) {
        competitorAppService.approveByOwner(id);
    }

    @PostMapping("/{id}/approvals/parent")
    public void approveByParent(@PathVariable CompetitorId id) {
        competitorAppService.approveByParent(id);
    }

    @GetMapping("/{id}")
    public CompetitorDto getCompetitor(@PathVariable CompetitorId id) {
        return competitorDtoMapper.toCompetitorDto(competitorAppService.getCompetitor(id));
    }
}
