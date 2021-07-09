package com.workup.workup.controllers;

import com.workup.workup.dao.ProfileRepository;
import com.workup.workup.dao.ProjectsRepository;
import com.workup.workup.dao.UsersRepository;
import com.workup.workup.models.Profile;
import com.workup.workup.models.Project;
import com.workup.workup.models.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Controller
public class HomeController {
    private ProjectsRepository projectsDao;
    private ProfileRepository profileDao;
    private UsersRepository usersDao;
    private PasswordEncoder passwordEncoder;


    public HomeController(ProjectsRepository projectsRepository, ProfileRepository profileRepository, UsersRepository usersRepository, PasswordEncoder passwordEncoder){
        this.projectsDao = projectsRepository;
        this.profileDao = profileRepository;
        this.usersDao = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
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

        if (!StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        // saves user and instantiates new profile
        profile.setUser(usersDao.save(user));
        profileDao.save(profile);

        return "redirect:/login";
    }

    // NO LONGER NEEDED FOR HOME VIEW BUT COULD BE USED TO REFACTORED CLUNCKY WORKING CODE
    //Project index for Developers to view in their Home Page
//    @GetMapping("/home")
//    public String projectsIndex(Model model,
//                                @AuthenticationPrincipal User user){
//
//            model.addAttribute("userRole", user.getRole().getRole());
//            model.addAttribute("allProjects", projectsDao.findAll());
//        model.addAttribute("devProfiles", profileDao.getAllByUserRole_Id(user.getRole().getId()));
//        return "home";
//    }
    @GetMapping("/home")
    public String home(@Param("searchString") String keyword, Model model, @AuthenticationPrincipal User user) {
        if (user.getRole().getRole().equalsIgnoreCase("developer")) {
                List<Project> allProjects = projectsDao.findAll();
                model.addAttribute("allProjects", allProjects);
        } else {

                List<Profile> devProfiles = profileDao.findAll();
                model.addAttribute("devProfiles", devProfiles);
            }
        model.addAttribute("userRole", user.getRole().getRole());
        return "home";
    }


    //      METHODS NEEDED FOR SEARCH TO WORK DECENT
//    IMPROVEMENTS THAT ARE NEEDED ARE PARTIAL/FUZZY INTERPRETATION AND STATE IS ACTING FUNNY
    public List<Long> projectSearch(String searchString) {
        return projectsDao.projectSearch(searchString);
    }

    public List<Long> devSearch(String searchString) {
        return profileDao.devSearch(searchString);
    }

    @GetMapping("/search")
    public String search(@Param("searchString") String keyword, Model model, @AuthenticationPrincipal User user) {
        if (user.getRole().getRole().equalsIgnoreCase("developer")) {
            List<Long> searchResult = projectSearch(keyword);
            if (searchResult.isEmpty()) {
                List<Project> searchResults = projectsDao.findAll();
                model.addAttribute("searchResults", searchResults);
                model.addAttribute("searchString", "No Results for '" + keyword + "'");
                model.addAttribute("pageTitle", "No Results for '" + keyword + "'");
            } else {
                List<Project> searchResults = projectsDao.findAllById(searchResult);
                model.addAttribute("searchResults", searchResults);
                model.addAttribute("searchString", "Search Results for '" + keyword + "'");
                model.addAttribute("pageTitle", "Search Results for '" + keyword + "'");
            }
        } else {
            List<Long> searchResult = devSearch(keyword);
            if (searchResult.isEmpty()) {
                List<Profile> searchResults = profileDao.findAll();
                model.addAttribute("searchResults", searchResults);
                model.addAttribute("searchString", "No Results for '" + keyword + "'");
                model.addAttribute("pageTitle", "No Results for '" + keyword + "'");
            } else {
                List<Profile> searchResults = profileDao.findAllById(searchResult);
                model.addAttribute("searchResults", searchResults);
                model.addAttribute("searchString", "Search Results for '" + keyword + "'");
                model.addAttribute("pageTitle", "Search Results for '" + keyword + "'");
            }
        }
        model.addAttribute("userRole", user.getRole().getRole());
        return "/search_result";
    }


}
