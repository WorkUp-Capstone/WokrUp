package com.workup.workup.controllers;

import com.workup.workup.dao.CategoryRepository;
import com.workup.workup.dao.ProfileRepository;
import com.workup.workup.dao.ProjectsRepository;
import com.workup.workup.dao.UsersRepository;
import com.workup.workup.models.Category;
import com.workup.workup.models.Profile;
import com.workup.workup.models.Project;
import com.workup.workup.models.User;
import com.workup.workup.services.EmailService;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.sql.Date;
import java.util.List;


@Controller
public class ProjectController {

    private final CategoryRepository categoryDao;
    private final ProjectsRepository projectDao;
    private final EmailService emailService;
    private final UsersRepository userDao;
    private final ProfileRepository profilesDao;

    public ProjectController(CategoryRepository categoryDao, ProjectsRepository projectDao, EmailService emailService, UsersRepository userDao, ProfileRepository profilesDao) {
        this.categoryDao = categoryDao;
        this.projectDao = projectDao;
        this.emailService = emailService;
        this.userDao = userDao;
        this.profilesDao = profilesDao;
    }

    //display ALL projects
    @GetMapping("/owner-profile/projects")
    public String projectsIndex(Model model) {
        model.addAttribute("allProjects", projectDao.findAll());
        return "projects/index"; // ?? may need return refactor
    }

    /**
     * Lines below commented in the event we need to show one project outside of the card view in the projects' index (dev/home) *note that show.html does not exist
     */
    //display selected single project
//    @GetMapping("/owner-profile/projects/{id}")
//    public String showProject(@PathVariable long id, Model model){
//        model.addAttribute("showProject", projectDao.getById(id));
//        return "projects/show";
//    }

    //create a Project
    @GetMapping("/owner-profile/projects/create")
    public String viewProjectCreateForm(Model model, Model categoryModel) {
        model.addAttribute("project", new Project());
        categoryModel.addAttribute("categoryList", categoryDao.findAll());
        return "projects/create";
    }

    @PostMapping("/projects/create")
    public String createProject(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "categories") List<Category> categoryList,
            @RequestParam(name = "status") String status,
            @AuthenticationPrincipal User user) {

        Project newProject = new Project();
        newProject.setTitle(title);
        newProject.setDescription(description);
        newProject.setCategories(categoryList);
        newProject.setStatus(status);
        newProject.setCreationDate(new Date(System.currentTimeMillis()));
        newProject.setUser(user);
        projectDao.save(newProject);
        return "redirect:/owner-profile";

    }

    //need to create one for ALL projects in a list method


    //edit selected project
    @GetMapping("/projects/{id}/edit")
    public String editProjectForm(Model model, @PathVariable Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Project projectToEdit = projectDao.getById(id);
        model.addAttribute("editProject", projectToEdit);
        return "projects/edit";
    }

    //edit and save project

    /**
     * TODO: need to include @RequestParams for categories and possibly files?
     */

    @PostMapping("/projects/{id}/edit")
    public String editProject(@PathVariable Long id,
                              @RequestParam(name = "title") String title,
                              @RequestParam(name = "description") String description,
                              @RequestParam(name = "status") String status
                              //,@RequestParam(name="categories")Category categories
    ) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Project project = projectDao.getById(id);

        project.setUser(user);
        project.setTitle(title);
        project.setDescription(description);
        project.setStatus(status);
        //project.setCategories((List<Category>)categories);

        projectDao.save(project);
        return "redirect:/owner-profile";
    }


//      METHODS NEEDED FOR SEARCH TO WORK DECENT
//    IMPROVEMENTS THAT ARE NEEDED ARE PARTIAL/FUZZY INTERPRETATION AND STATE IS ACTING FUNNY
    public List<Long> projectSearch(String searchString) {
        return projectDao.projectSearch(searchString);
    }

    public List<Long> devSearch(String searchString) {
        return profilesDao.devSearch(searchString);
    }

    @GetMapping("/search")
    public String search(@Param("searchString") String keyword, Model model, @AuthenticationPrincipal User user) {
        if (user.getRole().getRole().equalsIgnoreCase("developer")) {
            List<Long> searchResult = projectSearch(keyword);
            if (searchResult.isEmpty()) {
                List<Project> searchResults = projectDao.findAll();
                model.addAttribute("searchResults", searchResults);
                model.addAttribute("searchString", "No Results for '" + keyword + "'");
                model.addAttribute("pageTitle", "No Results for '" + keyword + "'");
            } else {
                List<Project> searchResults = projectDao.findAllById(searchResult);
                model.addAttribute("searchResults", searchResults);
                model.addAttribute("searchString", "Search Results for '" + keyword + "'");
                model.addAttribute("pageTitle", "Search Results for '" + keyword + "'");
            }
        } else {
            List<Long> searchResult = devSearch(keyword);
            if (searchResult.isEmpty()) {
                List<Profile> searchResults = profilesDao.findAll();
                model.addAttribute("searchResults", searchResults);
                model.addAttribute("searchString", "No Results for '" + keyword + "'");
                model.addAttribute("pageTitle", "No Results for '" + keyword + "'");
            } else {
                List<Profile> searchResults = profilesDao.findAllById(searchResult);
                model.addAttribute("searchResults", searchResults);
                model.addAttribute("searchString", "Search Results for '" + keyword + "'");
                model.addAttribute("pageTitle", "Search Results for '" + keyword + "'");
            }
        }
        return "projects/search_result";
    }


}
