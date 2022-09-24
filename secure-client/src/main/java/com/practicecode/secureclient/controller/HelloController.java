package com.practicecode.secureclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${message: Hello User}")
    private String message;

    @GetMapping("/hello")
    public String hello() {
        return message;
    }
}
