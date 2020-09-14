package binarek.robio.registration.rest.competitor;

import binarek.robio.registration.app.CompetitorAppService;
import binarek.robio.registration.app.CreateCompetitorCommand;
import binarek.robio.registration.domain.competitor.Competitor;
import binarek.robio.registration.domain.competitor.CompetitorId;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration/competitors")
public class CompetitorController {

    private final CompetitorAppService competitorAppService;

    public CompetitorController(CompetitorAppService competitorAppService) {
        this.competitorAppService = competitorAppService;
    }

    @PostMapping
    public Competitor createCompetitor(@RequestBody CreateCompetitorCommand competitor) {
        competitorAppService.createCompetitor(competitor);
        return competitorAppService.getCompetitorByEmail(competitor.getEmail());
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
    public Competitor getCompetitor(@PathVariable CompetitorId id) {
        return competitorAppService.getCompetitorById(id);
    }
}
