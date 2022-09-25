package com.practicecode.secureclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class HelloController {

    @Value("${default.test.message: Hello User}")
    private String message;

    @GetMapping("/hello")
    public String hello() {
        return message;
    }
}
