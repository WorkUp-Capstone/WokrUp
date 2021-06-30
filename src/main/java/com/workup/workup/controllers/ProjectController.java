package com.workup.workup.controllers;
import com.workup.workup.dao.ProjectsRepository;
import com.workup.workup.dao.UsersRepository;
import com.workup.workup.models.EmailService;
import com.workup.workup.models.Project;
import com.workup.workup.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    //save created project
    @PostMapping("/owner-profile/projects/create")
    public String projectCreateForm(@ModelAttribute Project project){

        User user = userDao.getById(1L);
        project.setOwnerUser(user);
        Project saveProject = projectDao.save(project);

        return "redirect:/owner-profile/projects/" + saveProject.getId();
    }

    //edit selected project
    @GetMapping("/owner-profile/projects/edit{id}")
    public String editProjectForm(@PathVariable long id, Model model){
        Project project = projectDao.getById(id);
        model.addAttribute("project", project);
        return "projects/edit";
    }

    //edit and save project
    /** TODO: need to include @RequestParams for categories and possibly files? */

    @PostMapping("/owner-profile/projects/edit{id}")
    public String editProject(@PathVariable long id, @RequestParam(name="title") String title, @RequestParam(name="description") String description){
        Project project = projectDao.getById(id);

        project.setTitle(title);
        project.setDescription(description);

        projectDao.save(project);
        return "redirect:/projects/{id}"; //where are we redirecting them? Profile or home
    }
}
