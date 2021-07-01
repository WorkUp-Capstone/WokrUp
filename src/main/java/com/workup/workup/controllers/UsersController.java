package com.workup.workup.controllers;

import com.workup.workup.dao.ProfileRepository;
import com.workup.workup.dao.ProjectsRepository;
import com.workup.workup.dao.UsersRepository;
import com.workup.workup.models.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsersController {

    private UsersRepository usersDao;
    private ProfileRepository profileDao;
    private ProjectsRepository projectsDao;

    public UsersController(UsersRepository usersRepository, ProjectsRepository projectsRepository, ProfileRepository profileRepository) {
        usersDao = usersRepository;
        projectsDao = projectsRepository;
        profileDao = profileRepository;
    }

    //View Single Profile:
    @GetMapping("/owner-profile/{id}")
    public String showOwnerProfile(@PathVariable long id, Model model){
        Profile profile = profileDao.findById(id);
        model.addAttribute("ownerProfile", profile);
        return "users/view-profile";
    }

    //edit selected profile
    @GetMapping("/owner-profile/edit/{id}")
    public String editProfileForm(@PathVariable long id, Model model){
        Profile profileToEdit = profileDao.findById(id);
        model.addAttribute("editOwnerProfile", profileToEdit);
        return "users/edit-profile";
    }

    //edit and save profile
    @PostMapping("/owner-profile/edit/{id}")
    public String editProfile(@PathVariable long id,@RequestParam(name = "about") String about,
                              @RequestParam(name = "portfolio_link") String portfolio_link,
                              @RequestParam(name = "resume_link") String resume_link,
                              @RequestParam(name = "city") String city,
                              @RequestParam(name = "state") String state,
                              @RequestParam(name = "phone_number") String phone_number,
                              @RequestParam(name = "profile_image_url") String profile_image_url){
        // find post
        Profile foundProfile = profileDao.getById(id);
        // edit post
        foundProfile.setAbout(about);
        foundProfile.setPortfolio_link((portfolio_link));
        foundProfile.setResume_link(resume_link);
        foundProfile.setCity(city);
        foundProfile.setState(state);
        foundProfile.setPhone_number(phone_number);
        foundProfile.setProfile_image_url(profile_image_url);
        // save changes
        profileDao.save(foundProfile);
        return "redirect:/owner-profile/{id}";
    }

    //TODO: edit user attributes (First name, last name, password)
}
