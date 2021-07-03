package com.workup.workup.controllers;

import com.workup.workup.dao.ProfileRepository;
import com.workup.workup.dao.ProjectsRepository;
import com.workup.workup.dao.UsersRepository;
import com.workup.workup.models.Profile;
import com.workup.workup.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestParam;

import javax.management.relation.Role;
import java.util.List;

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
        Profile profile = new Profile();
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        User newUser = usersDao.save(user);
        profile.setUser(newUser);
        user.setProfile(profile);
        profileDao.save(profile);
        usersDao.save(newUser);



       //instantiate a new profile


        // set profile user to saved user in db

        // use the profile repo to save the new profile

        return "redirect:/login";
    }

    //TODO: need to reach role parameter for a user
    //Project index for Developers to view in their Home Page
    @GetMapping("/home")
    public String projectsIndex(Model model,
                                @AuthenticationPrincipal User user){
            model.addAttribute("userRole", user.getRole());
            model.addAttribute("allProjects", projectsDao.findAll());
        model.addAttribute("developerProfile", user.getProfile());
        return "home";
    }


}
