package binarek.robio.registration.rest;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration/competitors")
public class CompetitorController {

    @GetMapping
    public String test() {
        return "ola";
    }
}
