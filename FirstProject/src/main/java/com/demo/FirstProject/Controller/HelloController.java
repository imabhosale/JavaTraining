package com.demo.FirstProject.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/home")
@RestController
public class HelloController {

    @GetMapping("")
    public String home() {
        return "Hello World";
    }
}
