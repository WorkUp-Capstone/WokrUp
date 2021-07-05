package com.workup.workup.controllers;

import com.workup.workup.dao.ProjectsRepository;
import com.workup.workup.dao.UsersRepository;
import com.workup.workup.models.Category;
import com.workup.workup.models.Project;
import com.workup.workup.models.User;
import com.workup.workup.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.workup.workup.models.Status;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.sql.Date;
import java.util.List;


@Controller
public class ProjectController {

private final ProjectsRepository projectDao;
private final EmailService emailService;
private final UsersRepository userDao;

public ProjectController(ProjectsRepository projectDao, EmailService emailService, UsersRepository userDao){
    this.projectDao = projectDao;
    this.emailService = emailService;
    this.userDao = userDao;

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
    public String viewProjectCreateForm(Model model){
        model.addAttribute("project", new Project());
        return "projects/create";
    }

    @PostMapping("/projects/create")
    public String createProject(
                              @RequestParam(name = "title") String title,
                              @RequestParam(name = "description") String description,
                              @RequestParam(name = "status") Status status,

                              @AuthenticationPrincipal User user) {

        Project newProject = new Project();
        Status newStatus = new Status();

        newProject.setTitle(title);
        newProject.setDescription(description);
        newProject.setCreationDate(new Date(System.currentTimeMillis()));
        newProject.setOwnerUser(user);
        newProject.setStatus(status);

        projectDao.save(newProject);
        return "redirect:/owner-profile";

    }

    //edit selected project
    @GetMapping("/projects/edit")
    public String editProjectForm(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Project projectToEdit = projectDao.getProjectByOwnerUserIs(user);
        model.addAttribute("editProject", projectToEdit);
        return "projects/edit";
    }

    //edit and save project
    /** TODO: need to include @RequestParams for categories and possibly files? */

    @PostMapping("/projects/edit")
    public String editProject(@RequestParam(name="title") String title,
                              @RequestParam(name="description") String description,
                              @RequestParam(name="status") Status status
                              //,@RequestParam(name="categories")Category categories
                              ){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Project project = projectDao.getProjectByOwnerUserIs(user);

        project.setOwnerUser(user);
        project.setTitle(title);
        project.setDescription(description);
        project.setStatus(status);
        //project.setCategories((List<Category>)categories);

        projectDao.save(project);
        return "redirect:/owner-profile";

    }


//    need to discuss postmapping redirect plan as well as confirm how we will handle search ie. categories/description
//    @GetMapping("/search")
//   public String searchProject(@PathVariable String searchString, Model model){
//
//    }


}
