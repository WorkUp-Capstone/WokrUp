package com.workup.workup.controllers;

import com.workup.workup.dao.*;
import com.workup.workup.models.*;
import com.workup.workup.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import com.workup.workup.models.Status;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.sql.Date;
import java.util.List;


@Controller
public class ProjectController {

    private final CategoryRepository categoryDao;
private final ProjectsRepository projectDao;
private final EmailService emailService;
private final UsersRepository userDao;
private final ProfileRepository profileDao;
private final ImagesRepository imageDao;

    @Value("${filestack.api.key}")
    private String filestackApi;

public ProjectController(CategoryRepository categoryDao, ProjectsRepository projectDao, EmailService emailService, UsersRepository userDao, ProfileRepository profileDao,
                         ImagesRepository imageDao){
    this.categoryDao = categoryDao;
    this.projectDao = projectDao;
    this.emailService = emailService;
    this.userDao = userDao;
    this.profileDao = profileDao;
    this.imageDao = imageDao;

}

    //display ALL projects
    @GetMapping("/owner-profile/projects")
    public String projectsIndex(Model model){
        model.addAttribute("allProjects", projectDao.findAll());
        return "projects/index"; // ?? may need return refactor
    }

/**Lines below commented in the event we need to show one project outside of the card view in the projects' index (dev/home) *note that show.html does not exist*/
    //display selected single project
//    @GetMapping("/owner-profile/projects/{id}")
//    public String showProject(@PathVariable long id, Model model){
//        model.addAttribute("showProject", projectDao.getById(id));
//        return "projects/show";
//    }

    //create a Project
    @GetMapping("/owner-profile/projects/create")
    public String viewProjectCreateForm(Model model, Model projectModel, Model categoryModel){
        projectModel.addAttribute("project", new Project());
        categoryModel.addAttribute("categoryList", categoryDao.findAll());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User logged = userDao.getById(user.getId());

        List<ProjectImage> projectImageList = imageDao.getAllProjectImageByProjectId(logged.getId());
        model.addAttribute("projectImageList", projectImageList);

        return "projects/create";
    }

    @PostMapping("/projects/create")
    public String createProject(
                              @RequestParam(name = "title") String title,
                              @RequestParam(name = "description") String description,
                              @RequestParam(name = "categories") List<Category> categoryList, @RequestParam(name = "status") String status,
                              @AuthenticationPrincipal User user) {

        Project newProject = new Project();
        newProject.setTitle(title);
        newProject.setDescription(description);
        newProject.setCategories(categoryList);
        newProject.setCreationDate(new Date(System.currentTimeMillis()));
        newProject.setStatus(status);
        newProject.setUser(user);
        projectDao.save(newProject);
        return "redirect:/owner-profile";

    }

    @GetMapping("/owner-profile/projects/images")
    public String viewProjectImages(Model model){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User logged = userDao.getById(user.getId());

        List<ProjectImage> projectImageList = imageDao.getAllProjectImageByProjectId(logged.getId());
        model.addAttribute("projectImageList", projectImageList);

        return "projects/view-project-images";
    }

    //edit selected project
    @GetMapping("/projects/{id}/edit")
    public String editProjectForm(Model model, @PathVariable Long id){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Project projectToEdit = projectDao.getById(id);
        model.addAttribute("editProject", projectToEdit);
        return "projects/edit";
    }

    //edit and save project
    /** TODO: need to include @RequestParams for categories and possibly files? */

    @PostMapping("/projects/{id}/edit")
    public String editProject(@PathVariable Long id,
                              @RequestParam(name="title") String title,
                              @RequestParam(name="description") String description,
                              @RequestParam(name="status") String status
                              //,@RequestParam(name="categories")Category categories
                              ){

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

//    //create project images:
    @GetMapping("/owner-profile/projectImg/{id}/add")
    public String viewProjectImagesForm(Model model, @PathVariable Long id){

//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Project addProjectImg = projectDao.getById(id);
        model.addAttribute("addProjectImg", addProjectImg);
        model.addAttribute("filestackapi", filestackApi);
        //model.addAttribute("project", projectDao.getProjectByUserIs(user));
//        model.addAttribute("image", imageDao.getImageByProjectId(user));

        return "projects/add-project-img";
    }

    //save project images:
    @PostMapping("/owner-profile/projectImg/{id}/add")
    public String saveProjectImages(@PathVariable Long id, @RequestParam(name = "project_img") String path){

ProjectImage image = new ProjectImage();

       Project project = projectDao.getById(id);

        //image.setId(id);
        image.setPath(path);
        image.setProject(project);


        imageDao.save(image);

        return "redirect:/owner-profile";
    }


//    need to discuss postmapping redirect plan as well as confirm how we will handle search ie. categories/description
//    @GetMapping("/search")
//   public String searchProject(@PathVariable String searchString, Model model){
//
//    }


}
