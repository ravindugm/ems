package com.practicecode.secureclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private String message;

    @GetMapping("/hello")
    public String hello() {
//        return "Hello, your code us working";
        return message;
    }
}
