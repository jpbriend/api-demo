package fr.mudak.apidemo;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String version() {
        return new String("Hello World!");
    }
}
