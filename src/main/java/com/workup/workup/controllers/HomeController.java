package com.workup.workup.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    //View Single Profile:
    @GetMapping("/owner-profile/{id}")
    public String showOwnerProfile(@PathVariable long id, Model model){
        //model.addAttribute("ownerProfile", daoName.getById(id));
        return "users/owner-profile";
    }

    //edit selected profile
    @GetMapping("/owner-profile/edit{id}")
    public String editProfileForm(@PathVariable long id, Model model){
        //Object object = daoname.getBy(id);
        //model.addAttribute("attributeName", attributeName);
        return "users/edit-owner-profile";
    }

    //edit and save profile
    @PostMapping("/owner-profile/edit{id}")
    public String editProfile(@PathVariable long id){
        //need to include @RequestParams, Dao.getById(id), setters, dao.save(object)
        return "redirect:/owner-profile/{id}";
    }


}
