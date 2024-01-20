package com.sportsmatch.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class APIController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
