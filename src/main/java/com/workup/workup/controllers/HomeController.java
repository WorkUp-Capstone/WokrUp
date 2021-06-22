package com.workup.workup.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String welcome(){
        return "welcome";
    }

    @GetMapping("/register")
    public String register(){
        return "registration";
    }

}
