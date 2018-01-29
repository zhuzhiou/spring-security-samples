package samples.security.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class SampleController {

    @GetMapping(path = "/sample")
    public String sample() {
        return "congratulations";
    }
}
