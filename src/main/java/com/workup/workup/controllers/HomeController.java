package com.workup.workup.controllers;

import com.workup.workup.dao.CategoryRepository;
import com.workup.workup.dao.ImagesRepository;
import com.workup.workup.dao.ProfileRepository;
import com.workup.workup.dao.ProjectsRepository;
import com.workup.workup.dao.UsersRepository;
import com.workup.workup.models.Category;
import com.workup.workup.models.Profile;
import com.workup.workup.models.ProjectImage;
import com.workup.workup.models.User;
import com.workup.workup.services.CategoryService;
import com.workup.workup.services.Email.EmailServiceImplementation;
import com.workup.workup.services.ProfileService;
import com.workup.workup.services.ProjectService;
import com.workup.workup.services.UserService;
import com.workup.workup.services.validation.Validation;
import org.apache.commons.lang3.StringUtils;
import com.workup.workup.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;


@Controller
public class HomeController {
    private ProjectsRepository projectsDao;
    private ProfileRepository profileDao;
    private UsersRepository usersDao;
    private PasswordEncoder passwordEncoder;
    private CategoryRepository categoryDao;
    private ImagesRepository imageDao;
  
    private final EmailServiceImplementation email;
    @Autowired private ProjectService projectService;
    @Autowired private UserService userService;
    @Autowired private ProfileService profileService;
    @Autowired private CategoryService categoryService;


    public HomeController(ProjectsRepository projectsRepository, ProfileRepository profileRepository, UsersRepository usersRepository, ImagesRepository imagesRepository,PasswordEncoder passwordEncoder, EmailServiceImplementation email){
        this.projectsDao = projectsRepository;
        this.profileDao = profileRepository;
        this.usersDao = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.imageDao = imagesRepository;
        this.email = email;
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
    public ModelAndView saveUser(@ModelAttribute @Valid User user){
        Profile profile = new Profile();
        String password = user.getPassword();
        String email = user.getEmail();
        List<User> allUsers = usersDao.findAll();
        String passwordRepeat = user.getPasswordRepeat();
        ModelAndView modelAndView = new ModelAndView();
        boolean strongPassword = Pattern.matches("[a-zA-Z0-9]", password);
        boolean isPasswordConfirmed = password.equals(passwordRepeat);

        Validation validate = new Validation();
        modelAndView.setViewName("registration");
        modelAndView.addObject("user", user);

        if (validate.emailHasError(email)) {
            String emailError = "Input a proper email (___@mailservice.com)";
            modelAndView.addObject("emailError", emailError);
            return modelAndView;
        }
        if (validate.emailExists(email, allUsers)) {
            String emailExistsError = "This email is already registered";
            modelAndView.addObject("emailExistsError", emailExistsError);
            return modelAndView;
        }
        if (validate.passwordHasError(password) || strongPassword) {
            String passwordError = "Password should be at least 8 digits long and must contain at least one special character";
            modelAndView.addObject("passwordError", passwordError);
            return modelAndView;
        }
        if (!isPasswordConfirmed) {
            String passwordMatchError = "Passwords do not match";
            modelAndView.addObject("confirmPass", passwordMatchError);
            return modelAndView;
        }
        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
            user.setPasswordRepeat(passwordEncoder.encode(passwordRepeat));
            // saves user and instantiates new profile
            profile.setUser(usersDao.save(user));
            profileDao.save(profile);

        }
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("userRole", user.getRole().getRole());
        List<Profile> profiles = profileDao.findAll();
        List<Profile> devProfiles = new ArrayList<>();
        List<Project> projects = projectsDao.findAll();
        List<Project> openProjects = new ArrayList<>();

        for(Project project : projects) {
            if (project.getStatus().contains("open")) {
                openProjects.add(project);
            }
        }

        for(Profile profile : profiles) {
            if (profile.getUser().getRole().getId() == 2) {
                devProfiles.add(profile);
            }
        }
        model.addAttribute("openProjects", openProjects);
        model.addAttribute("devProfiles", devProfiles);
        return "home";
    }

    @GetMapping("/home/project/{id}/images")
    public String viewProjectImages(Model model, @PathVariable Long id){

        List<ProjectImage> projectImage = imageDao.findAllProjectImagesByProjectId(id);

model.addAttribute("projectImageList", projectImage);

        return "projects/view-project-images";
    }


    //      METHODS NEEDED FOR SEARCH TO WORK DECENT
//    IMPROVEMENTS THAT ARE NEEDED ARE PARTIAL/FUZZY INTERPRETATION AND STATE IS ACTING FUNNY
    public List<Long> projectSearch(String searchString) {
        return projectsDao.projectSearch(searchString);
    }

    public List<Long> devSearch(String searchString) {
        return profileDao.devSearch(searchString);
    }


    // THINGS TO ADD REMOVE MINIMUM CHARACTERS TO SEARCH
    @GetMapping("/search")
    public String search(@Param("searchString") String keyword, Model model, @AuthenticationPrincipal User user) {
        List<Project> projectSearchResults;
        List<Profile> profileSearchResults;
        if (user.getRole().getRole().equalsIgnoreCase("developer")) {
            List<Long> searchResult = projectSearch(keyword);
            if (searchResult.isEmpty()) {
                projectSearchResults = projectsDao.findAll();
                model.addAttribute("searchResults", projectSearchResults);
                model.addAttribute("searchString", "No Results for '" + keyword + "'");
                model.addAttribute("pageTitle", "No Results for '" + keyword + "'");
            } else {
                projectSearchResults = projectsDao.findAllById(searchResult);
                model.addAttribute("searchResults", projectSearchResults);
                model.addAttribute("searchString", "Search Results for '" + keyword + "'");
                model.addAttribute("pageTitle", "Search Results for '" + keyword + "'");
            }
        } else {
            List<Long> searchResult = devSearch(keyword);
            if (searchResult.isEmpty()) {
                profileSearchResults = profileDao.findAll();
                model.addAttribute("searchResults", profileSearchResults);
                model.addAttribute("searchString", "No Results for '" + keyword + "'");
                model.addAttribute("pageTitle", "No Results for '" + keyword + "'");
            } else {
                profileSearchResults = profileDao.findAllById(searchResult);
                model.addAttribute("searchResults", profileSearchResults);
                model.addAttribute("searchString", "Search Results for '" + keyword + "'");
                model.addAttribute("pageTitle", "Search Results for '" + keyword + "'");
            }
        }
        model.addAttribute("userRole", user.getRole().getRole());
        return "search_result";
    }

    @PostMapping("/home")
    public String contactUser(@AuthenticationPrincipal User user, @RequestParam(name = "profileID") Long devId, @RequestParam String keyword, Model model) throws MessagingException, IOException {
        Profile primaryProfile = profileDao.getProfileByUserId(user.getId());
        User contactUser = usersDao.getById(devId);
        User primaryUser = usersDao.getById(user.getId());
        HashMap<String,Object> emailbody = new HashMap<String,Object>();
        emailbody.put("contactUser", contactUser);
        emailbody.put("primaryProfile", primaryProfile);
        emailbody.put("primaryUser", primaryUser);
        email.sendUserMessageUsingThymeleafTemplate(contactUser.getEmail(), contactUser.getFirstName() + contactUser.getFirstName(), emailbody);
        return "redirect:/home";
    }

    @GetMapping("/home/search")
    public String searchResults(Model model, @AuthenticationPrincipal User user, String keyword) {
        model.addAttribute("userRole", user.getRole().getRole());
        List<Profile> profiles = profileService.getProfilesByKeyword(keyword);
        List<Profile> foundProfiles = new ArrayList<>();
        List<Project> projects = projectService.getProjectsByKeyword(keyword);
        List<Project> foundProjects = new ArrayList<>();
        Category category = categoryService.findByName(keyword);
        List<Project> foundCategories = projectsDao.findByCategoriesContains(category);
        System.out.println(foundCategories);
        model.addAttribute("keyword", keyword);

//        model.addAttribute("foundProfiles", profileService.getProfilesByKeyword(keyword));
//        model.addAttribute("foundProfiles", userService.getUsersByKeyword(keyword));
//        model.addAttribute("foundProjects", projectService.getProjectsByKeyword(keyword));

        for(Project project : projects) {
                if (project.getStatus().contains("open")) {

                    foundProjects.add(project);
                }
        }

        for(Profile profile : profiles) {
                if (profile.getUser().getRole().getId() == 2) {
//                    model.addAttribute("foundProjects", userService.getUsersByKeyword(keyword));
                    foundProfiles.add(profile);
                }
        }
        // TODO: Category search works, needs full connection
        model.addAttribute("foundProjects", foundProjects);
        model.addAttribute("foundProfiles", foundProfiles);
        return "search";
    }

    @PostMapping("/home/search")
    public String searchHome(@AuthenticationPrincipal User user, @RequestParam(name = "profileID") Long devId, @RequestParam String keyword, Model model) throws MessagingException, IOException {
        profileDao.getProfileByUserId(user.getId());
        model.addAttribute("keyword", keyword);
        model.addAttribute("foundProfiles", profileService.getProfilesByKeyword(keyword));
        model.addAttribute("foundUsers", userService.getUsersByKeyword(keyword));
        model.addAttribute("foundProjects", projectService.getProjectsByKeyword(keyword));
        return "redirect:/home";
    }




    @PostMapping("/home/contact")
    public String contactProject(@AuthenticationPrincipal User user, @RequestParam(name = "projectID") Long projectId) throws MessagingException, IOException, MessagingException, IOException {
        Project project = projectsDao.getProjectById(projectId);
        Profile primaryProfile = profileDao.getProfileByUserId(user.getId());
        User contactUser = usersDao.getById(project.getUser().getId());
        User primaryUser = usersDao.getById(user.getId());
        HashMap<String,Object> emailbody = new HashMap<String,Object>();
        emailbody.put("project", project);
        emailbody.put("primaryProfile", primaryProfile);
        emailbody.put("primaryUser", primaryUser);
        project.setStatus("in progress");
        projectsDao.saveAndFlush(project);
        email.sendProjectMessageUsingThymeleafTemplate(contactUser.getEmail(), contactUser.getFirstName() + contactUser.getFirstName(), emailbody);
        return "redirect:/home";
    }

    @GetMapping("/about-us")
    public String about(){
        return "about-us";
    }

    @GetMapping("/tos")
    public String tos(){
        return "tos";
    }
}
