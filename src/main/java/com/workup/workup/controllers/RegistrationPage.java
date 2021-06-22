package com.workup.workup.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class RegistrationPage {

    @GetMapping("/")
    @ResponseBody
    public String RegistrationPage(){
        return "registration";
    }
}
