package com.example.learning_english.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/test/hello")
public class WelcomeController {

    @RequestMapping(method = RequestMethod.GET)
    public String hello(){
        return "hello heroku";
    }
}
