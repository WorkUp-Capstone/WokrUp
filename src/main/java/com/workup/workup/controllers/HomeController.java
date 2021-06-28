package com.workup.workup.controllers;

import com.workup.workup.dao.ProfileRepository;
import com.workup.workup.dao.ProjectsRepository;
import com.workup.workup.dao.UsersRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    private ProjectsRepository projectsDao;
    private ProfileRepository profileDao;
    private UsersRepository usersDao;

    public HomeController(ProjectsRepository projectsRepository, ProfileRepository profileRepository, UsersRepository usersRepository){
        projectsDao = projectsRepository;
        profileDao = profileRepository;
        usersDao = usersRepository;
    }

    @GetMapping("/")
    public String welcome(){
        return "welcome";
    }

    @GetMapping("/register")
    public String register(){
        return "registration";
    }

    // TODO: post mapping for register


    //Project index for Developers to view in their Home Page
    @GetMapping("/home-projects")
    public String projectsIndex(Model model){
        model.addAttribute("allProjects", projectsDao.findAll());
        return "home";
    }

    //Project index for Developers to view in their Home Page
    @GetMapping("/home-developers")
    public String developersIndex(Model model){
        model.addAttribute("allDevs", profileDao.findAll()); //need to find relationship for developer roles to pass in the parameter (this is most likely wrong)
        return "users/owner-profile";
    }




}
