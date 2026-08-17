package com.netpoint.clinicapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/test")
@RestController
public class HelloWorldController {
    @GetMapping
    String sayhello()
    {
        return "Hello World!";
    }
}
