package com.example.chap_11_business_server_2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(value = "/test", produces = "application/json" )
    public String test() {
        System.out.println("a------------------>");
        return "test";
    }
}