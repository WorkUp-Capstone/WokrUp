package com.workup.workup.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String welcome(){
        return "welcome";
    }

    @GetMapping("/register")
    public String register(){
        return "registration";
    }

    // TODO: post mapping for register
    
    
    //Where is profile if profile was already created?
    //DeveloperProfile
    @GetMapping("/DeveloperProfile")
    public String DeveloperProfile(){
        return "DeveloperProfile";
    }
    
    //OwnerProfile
    @GetMapping("/ClientProfile")
    public String ClientProfile(){
        return "ClientProfile";
    }
    
    @GetMapping("/partials/navbar")
    private String Navbar() {
        return "navbar";
    }
    
        @GetMapping("/partials/head")
    private String Head() {
        return "head";
    }
    
    @GetMapping("/partials/footer")
    private String Footer() {
        return "footer";
    }
    
    
}
