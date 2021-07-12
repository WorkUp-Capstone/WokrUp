package com.workup.workup.controllers;

import com.workup.workup.dao.*;
import com.workup.workup.models.*;
import com.workup.workup.services.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UsersController {

    private UsersRepository usersDao;
    private ProfileRepository profileDao;
    private ProjectsRepository projectsDao;
    private CategoryRepository categoryDao;
    private final ImagesRepository imageDao;

    //Filestack API Key Import:
    @Value("${filestack.api.key}")
    private String filestackApi;

    public UsersController(UsersRepository usersRepository, ProjectsRepository projectsRepository, ProfileRepository profileRepository, CategoryRepository categoryRepository,ImagesRepository imageDao) {
        usersDao = usersRepository;
        projectsDao = projectsRepository;
        profileDao = profileRepository;
        categoryDao = categoryRepository;
        this.imageDao = imageDao;
    }

    //View Single Profile:
    @GetMapping("/profile")
    public String showOwnerProfile(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Profile profile;
        User logged = usersDao.getById(user.getId());
        profile = profileDao.getProfileByUserIs(user);
        model.addAttribute("ownerProfile", profile);

        List<Project> projectList;
        projectList = projectsDao.getAllProjectsByUserIdIs(logged.getId());
        model.addAttribute("ownerProject", projectList);

        return "profile/view-profile";
    }

    //edit selected profile
    @GetMapping("/profile/edit")
    public String editProfileForm(Model profileModel, Model categoryModel, Model filestackModel){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

       categoryModel.addAttribute("categoryList", categoryDao.findAll());
        profileModel.addAttribute("editOwnerProfile", profileDao.getProfileByUserIs(user));
        filestackModel.addAttribute("filestackapi", filestackApi);

        return "profile/edit-profile";
    }

    //edit and save profile
    @PostMapping("/profile/edit")
    public String editProfile(@RequestParam(name = "about") String about,
                              @RequestParam(name = "portfolio_link") String portfolio_link,
                              @RequestParam(name = "resume_link") String resume_link,
                              @RequestParam(name = "city") String city,
                              @RequestParam(name = "state") String state,
                              @RequestParam(name = "categories") List<Category> categories,
                              @RequestParam(name = "phone_number") String phone_number){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Profile foundProfile = profileDao.getProfileByUserIs(user);
        foundProfile.setUser(user);
        foundProfile.setAbout(about);
        foundProfile.setPortfolio_link((portfolio_link));
        foundProfile.setResume_link(resume_link);
        foundProfile.setCity(city);
        foundProfile.setCategories(categories);
        foundProfile.setState(state);
        foundProfile.setPhone_number(phone_number);
        profileDao.save(foundProfile);
        return "redirect:/profile";
    }

    //create profile image:
    @GetMapping("/profile/profileImg/add")
    public String viewProfileImg(Model model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("filestackapi", filestackApi);
        model.addAttribute("userProfile", profileDao.getProfileByUserIs(user));

        return "profile/add-profile-img";
    }

    //Save profile image
    @PostMapping("/profile/profileImg/add")
    public String saveProfileImg(@RequestParam(name="profile_img") String profile_image_url){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Profile profile = profileDao.getProfileByUserIs(user);
        profile.setProfile_image_url(profile_image_url);

        profileDao.save(profile);
        return "redirect:/profile";
    }


    //TODO: edit user attributes (First name, last name, password)
}
