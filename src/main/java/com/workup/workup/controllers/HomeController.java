package com.workup.workup.controllers;

import com.workup.workup.dao.ProfileRepository;
import com.workup.workup.dao.ProjectsRepository;
import com.workup.workup.dao.UsersRepository;
import com.workup.workup.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class HomeController {
    private ProjectsRepository projectsDao;
    private ProfileRepository profileDao;
    private UsersRepository usersDao;
    private PasswordEncoder passwordEncoder;


    public HomeController(ProjectsRepository projectsRepository, ProfileRepository profileRepository, UsersRepository usersRepository, PasswordEncoder passwordEncoder){
        projectsDao = projectsRepository;
        profileDao = profileRepository;
        usersDao = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

//create user
    @GetMapping("/register")
    public String registerUser(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

//save user
    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user){
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        usersDao.save(user);
        return "redirect:/welcome"; //may need to redirect them somewhere else
    }


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
