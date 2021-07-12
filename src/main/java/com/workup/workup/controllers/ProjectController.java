package com.workup.workup.controllers;

import com.workup.workup.dao.*;
import com.workup.workup.models.*;
import org.springframework.beans.factory.annotation.Value;
import com.workup.workup.dao.CategoryRepository;
import com.workup.workup.dao.ProfileRepository;
import com.workup.workup.dao.ProjectsRepository;
import com.workup.workup.dao.UsersRepository;
import com.workup.workup.models.Category;
import com.workup.workup.models.Project;
import com.workup.workup.models.User;
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
private final ProfileRepository profileDao;
private final ProjectsRepository projectDao;
private final UsersRepository userDao;
private final ImagesRepository imageDao;

    @Value("${filestack.api.key}")
    private String filestackApi;

public ProjectController(CategoryRepository categoryDao, ProjectsRepository projectDao, UsersRepository userDao, ProfileRepository profileDao,
                         ImagesRepository imageDao){
    this.categoryDao = categoryDao;
    this.projectDao = projectDao;
    this.userDao = userDao;
    this.profileDao = profileDao;
    this.imageDao = imageDao;

}


    //display ALL projects
    @GetMapping("/profile/projects")
    public String projectsIndex(Model model) {
        model.addAttribute("allProjects", projectDao.findAll());
        return "projects/index"; // ?? may need return refactor
    }

    /**
     * Lines below commented in the event we need to show one project outside of the card view in the projects' index (dev/home) *note that show.html does not exist
     */
    //display selected single project
//    @GetMapping("/profile/projects/{id}")
//    public String showProject(@PathVariable long id, Model model){
//        model.addAttribute("showProject", projectDao.getById(id));
//        return "projects/show";
//    }

    //create a Project
    @GetMapping("/profile/projects/create")
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
        newProject.setStatus(status);
        newProject.setUser(user);
        projectDao.save(newProject);
        return "redirect:/profile";

    }

    @GetMapping("/profile/projects/images")
    public String viewProjectImages(Model model){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User logged = userDao.getById(user.getId());

        List<ProjectImage> projectImageList = imageDao.getAllProjectImageByProjectId(logged.getId());
        model.addAttribute("projectImageList", projectImageList);

        return "projects/view-project-images";
    }

    //edit selected project
    @GetMapping("/projects/{id}/edit")
    public String editProjectForm(Model model, @PathVariable Long id, Model categoryModel) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Project projectToEdit = projectDao.getById(id);
        model.addAttribute("editProject", projectToEdit);
        categoryModel.addAttribute("categoryList", categoryDao.findAll());
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
        return "redirect:/profile";
    }

//    //create project images:
    @GetMapping("/profile/projectImg/{id}/add")
    public String viewProjectImagesForm(Model model, @PathVariable Long id){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User logged = userDao.getById(user.getId());

        Project addProjectImg = projectDao.getById(id);
        model.addAttribute("addProjectImg", addProjectImg);
        model.addAttribute("filestackapi", filestackApi);

        List<ProjectImage> projectImageList = imageDao.getAllProjectImageByProjectId(addProjectImg.getId());

        model.addAttribute("projectImageList", projectImageList);
        //model.addAttribute("project", projectDao.getProjectByUserIs(user));
//        model.addAttribute("image", imageDao.getImageByProjectId(user));

        return "projects/add-project-img";
    }

    //save project images:
    @PostMapping("/profile/projectImg/{id}/add")
    public String saveProjectImages(@PathVariable Long id, @RequestParam(name = "project_img") String path){

ProjectImage image = new ProjectImage();

       Project project = projectDao.getById(id);

        //image.setId(id);
        image.setPath(path);
        image.setProject(project);


        imageDao.save(image);

        return "redirect:/profile";
    }






}
