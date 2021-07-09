package com.workup.workup.controllers;

import com.workup.workup.UserStorage;
import com.workup.workup.dao.CategoryRepository;
import com.workup.workup.dao.ProfileRepository;
import com.workup.workup.dao.ProjectsRepository;
import com.workup.workup.dao.UsersRepository;
import com.workup.workup.models.Category;
import com.workup.workup.models.Profile;
import com.workup.workup.models.Project;
import com.workup.workup.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
public class UsersController {

    private UsersRepository usersDao;
    private ProfileRepository profileDao;
    private ProjectsRepository projectsDao;
    private CategoryRepository categoryDao;

    public UsersController(UsersRepository usersRepository, ProjectsRepository projectsRepository, ProfileRepository profileRepository, CategoryRepository categoryRepository) {
        usersDao = usersRepository;
        projectsDao = projectsRepository;
        profileDao = profileRepository;
        categoryDao = categoryRepository;
    }

    //View Single Profile:
    @GetMapping("/owner-profile")
    public String showOwnerProfile(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Profile profile;
        User logged = usersDao.getById(user.getId());
        profile = profileDao.getProfileByUserIs(user);
        model.addAttribute("ownerProfile", profile);

        List<Project> projectList;
        projectList = projectsDao.getAllProjectsByUserIdIs(logged.getId());
        model.addAttribute("ownerProject", projectList);
        return "users/view-profile";
    }

    //edit selected profile
    @GetMapping("/owner-profile/edit")
    public String editProfileForm(@ModelAttribute Profile profileToEdit, Model profileModel, Model categoryModel){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       profileToEdit = profileDao.getProfileByUserIs(user);
       categoryModel.addAttribute("categoryList", categoryDao.findAll());
        profileModel.addAttribute("editOwnerProfile", profileToEdit);
        return "users/edit-profile";
    }

    //edit and save profile
    @PostMapping("/owner-profile/edit")
    public String editProfile(@RequestParam(name = "about") String about,
                              @RequestParam(name = "portfolio_link") String portfolio_link,
                              @RequestParam(name = "resume_link") String resume_link,
                              @RequestParam(name = "city") String city,
                              @RequestParam(name = "state") String state,
                              @RequestParam(name = "categories") List<Category> categories,
                              @RequestParam(name = "phone_number") String phone_number,
                              @RequestParam(name = "profile_image_url") String profile_image_url){
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
        foundProfile.setProfile_image_url(profile_image_url);

        profileDao.save(foundProfile);
        return "redirect:/owner-profile";
    }


//    @GetMapping("/fetchAllUsers")
//    public Set<String> fetchAll(){
//        String email = usersDao.findByEmail();
//        return UserStorage.getInstance().getUsers();
//    }





    //TODO: edit user attributes (First name, last name, password)
}
